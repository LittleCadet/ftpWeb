package com.myproj.ftp;

import com.myproj.tools.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量删除：支持删除服务器上不同目录下的文件
 * 沈燮
 * 2019/1/3
 **/
public class FtpBatchDelete
{
    private static final Logger logger = LoggerFactory.getLogger(FtpBatchDownload.class.getName());

    private FTPClient client = FtpUtil.init();

    //分隔符“/”
    private final String SPLIT_FORWARD_SLASH = "/";

    private final String IS_FILE = ".";

    //指定需要删除服务器的路径
    private List<String> remoteDeleteFilePaths = new ArrayList<String>();

    //删除失败的文件路径
    private List<String> fails = new ArrayList<String>();

    //批量删除服务器文件的txt的路径
    private String batchDeleteFilePath;

    /**
     * 删除指定目录下的指定文件，但是不能删除文件夹
     *
     * @return 判定是否已删除
     */
    public boolean batchDeleteFile()
    {
        boolean flag = false;

        if (isNotNull())
        {
            //连接到ftp
            if (FtpUtil.connectToFtp())
            {
                flag = deleteProcess();
            }
        }

        return flag;
    }

    /**
     * 删除的过程，文件存在才会删除，不存在则不删除
     * @return 只有执行删除成功时，返回true
     */
    public boolean deleteProcess()
    {
        int isExist = 0;

        Boolean flag = false;

        List<Boolean> flags = new ArrayList<Boolean>();

        try
        {
            for (String remoteDeleteFilePath : remoteDeleteFilePaths)
            {
                int index = remoteDeleteFilePath.lastIndexOf(SPLIT_FORWARD_SLASH);

                String fileName = remoteDeleteFilePath.substring(index + 1);

                String filePathName = remoteDeleteFilePath.substring(0, index);

                if (client.changeWorkingDirectory(filePathName))
                {
                    isExist = deleteDetailProcess(index, fileName, remoteDeleteFilePath);
                    flag = (isExist == 250 ? Boolean.TRUE : Boolean.FALSE);

                    if(!flag)
                    {
                        //如果删除失败，则保存删除失败的文件路径
                        fails.add(remoteDeleteFilePath);
                    }

                    flags.add(flag);
                }
                else
                {
                    logger.error("file not exist,no need to delete");
                    return true;
                }
            }
            if(CollectionUtils.isEmpty(fails))
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("method: deleteProcess():--------------All files delete successfully--------------");
                }
            }
            else
            {
                for(String remoteDeleteFilePath : fails)
                {
                    logger.error("method: deleteProcess():failed to delete remote file path:" + remoteDeleteFilePath);
                }
            }
            if(flags.contains(false))
            {
                return false;
            }
        }
        catch (IOException e)
        {
            logger.error("method: deleteProcess():something wrong with changing directory by ftp：exception:" + e);
            return false;
        }
        finally
        {
            FtpUtil.closeResources(null, null, client);
        }

        return true;
    }

    /**
     * 删除的过程
     *
     * @return 返回是否删除文件的返回码：返回码为250，则删除该文件成功，那么代表该文件夹存在,返回码为550，代表删除失败
     */
    public Integer deleteDetailProcess(Integer index, String fileName, String remoteDeleteFilePath)
    {
        int isExist = 0;

        List<Integer> isExists = new ArrayList<Integer>();

        if(logger.isDebugEnabled())
        {
            logger.debug("method: deleteDetailProcess():start to delete file: file path:" +remoteDeleteFilePath + ",file name is:"+fileName);
        }

        // 检验文件夹是否存在:通过删除该文件的方式来判定，如果返回码为250，则删除该文件成功，那么代表该文件夹存在,返回码为550，代表删除失败
        try
        {
            //文件名包含“.”，才会执行删除操作，否则会认为fileName是一个目录，会遍历所有文件，并删除
            if(fileName.contains(IS_FILE))
            {
                isExist = client.dele(fileName);

                if(logger.isDebugEnabled())
                {
                    logger.debug("method: deleteDetailProcess():file:" + (isExist == 250 ? "delete successfully" : "not exist") + ",file name is:" + fileName);
                }
            }
            else
            {
                //切换目录
                client.changeWorkingDirectory(remoteDeleteFilePath);

                //为linux主动开启被动模式
                client.enterLocalPassiveMode();

                //找出所有子文件，遍历删除
                FTPFile[] files = client.listFiles(remoteDeleteFilePath);
                for(FTPFile file:files)
                {
                    isExist = client.dele(file.getName());

                    isExists.add(isExist);

                    if(isExist != 250)
                    {
                        fails.add(remoteDeleteFilePath + SPLIT_FORWARD_SLASH +file.getName());
                    }

                    if(logger.isDebugEnabled())
                    {
                        logger.debug("method: deleteDetailProcess():file:" + (isExist == 250 ? "delete successfully" : "not exist") + ",file name is:" + fileName);
                    }
                }

                //只要有删除失败，那么就直接赋值为500（失败）
                if(isExists.contains(500) || isExists.contains(550))
                {
                    isExist = 500;
                }
            }
        }
        catch (IOException e)
        {
            logger.error("method: deleteDetailProcess():failed to delete file by ftp ：remoteDeleteFilePath：" + remoteDeleteFilePath + ",exception:" + e);

            isExist = 500;
        }

        return isExist;
    }

    /**
     * 从txt文件中获取需要批量删除的文件的路径
     *
     * @return 不为空时，返回true
     */
    public boolean isNotNull()
    {
        BufferedReader br = null;
        try
        {
            String line = null;
            br = new BufferedReader(new FileReader(batchDeleteFilePath));
            while (null != (line = br.readLine()))
            {
                if (line.contains(SPLIT_FORWARD_SLASH))
                {
                    remoteDeleteFilePaths.add(line.trim());
                }
            }

            if (CollectionUtils.isEmpty(remoteDeleteFilePaths))
            {
                logger.error("method: isNotNull():batch delete files are null,stop to delete");
                return false;
            }
        }
        catch (FileNotFoundException e)
        {
            logger.error("method: isNotNull():method:isNotNull is failed,e" + e);
            return false;
        }
        catch (IOException e)
        {
            logger.error("method: isNotNull():IO operation is failed,e" + e);
            return false;
        }
        finally
        {
            if(null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    logger.error("method: isNotNull():io close exception,e:" + e);
                }
            }
        }
        return true;
    }

    public String getBatchDeleteFilePath()
    {
        return batchDeleteFilePath;
    }

    public void setBatchDeleteFilePath(String batchDeleteFilePath)
    {
        this.batchDeleteFilePath = batchDeleteFilePath;
    }
}
