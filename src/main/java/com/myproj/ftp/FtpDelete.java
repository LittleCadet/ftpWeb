package com.myproj.ftp;

import com.myproj.tools.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * ftp的删除操作
 * @Author LettleCadet
 * @Date 2018/12/27
 */
public class FtpDelete
{
    private static final Logger logger = LoggerFactory.getLogger(FtpDelete.class.getName());

    //指定删除服务器的路径
    private String remoteDeleteFilePath;

    //分隔符“/”
    private final String SPLIT_FORWARD_SLASH = "/";

    private FTPClient client = FtpUtil.init();

    /**
     * 供以后定时任务使用
     * 删除指定目录下的指定文件，但是不能删除文件夹
     * @return 判定是否已删除
     */
    public boolean deleteFile()
    {
        boolean flag = false;

        if(isNotNull())
        {
            //连接到ftp
            if(FtpUtil.connectToFtp())
            {
                flag = deleteProcess();
            }
        }

        return flag;
    }

    /**
     * 删除指定目录下的指定文件，但是不能删除文件夹
     * @return 判定是否已删除
     */
    public boolean deleteFile(String remoteDeleteFilePath)
    {
        boolean flag = false;

        this.remoteDeleteFilePath = remoteDeleteFilePath;

        if(isNotNull())
        {
            //连接到ftp
            if(FtpUtil.connectToFtp())
            {
                flag = deleteProcess();
            }
        }

        return flag;
    }

    public boolean deleteProcess()
    {
        int isExist = 0;

        try
        {
            int index = remoteDeleteFilePath.lastIndexOf(SPLIT_FORWARD_SLASH);

            String fileName = remoteDeleteFilePath.substring(index+1);

            String filePathName = remoteDeleteFilePath.substring(0,index);

            if(client.changeWorkingDirectory(filePathName))
            {
                isExist = deleteDetailProcess(index,fileName);
            }
            else
            {
                logger.error("method:deleteProcess():file not exist,no need to delete");
                return true;
            }
        }
        catch (IOException e)
        {
            logger.error("method:deleteProcess():something wrong with changing directory by ftp：exception:" + e);
            return false;
        }
        finally
        {
            FtpUtil.closeResources(null,null,client);
        }

        return isExist==250?Boolean.TRUE:Boolean.FALSE;
    }

    /**
     * 删除的过程
     * @return 返回是否删除文件的返回码：返回码为250，则删除该文件成功，那么代表该文件夹存在,返回码为550，代表删除失败
     */
    public Integer deleteDetailProcess(Integer index,String fileName)
    {
        int isExist = 0;

        if(logger.isDebugEnabled())
        {
            logger.debug("method:deleteDetailProcess():file start to delete:remote path is :" + remoteDeleteFilePath + ",file name is:" + fileName);
        }

        // 检验文件夹是否存在:通过删除该文件的方式来判定，如果返回码为250，则删除该文件成功，那么代表该文件夹存在,返回码为550，代表删除失败
        try
        {
            isExist = client.dele(fileName);

            if(logger.isDebugEnabled())
            {
                logger.debug("method:deleteDetailProcess():file" + (isExist==250?"delete successfully":"not exist") + ",file name is :" + fileName);
            }
        }
        catch (IOException e)
        {
            logger.error("method:deleteDetailProcess():failed to delete file by ftp ：remoteDeleteFilePath："+remoteDeleteFilePath+",exception:"+e);
            isExist = 550;
        }

        return isExist;
    }

    /**
     * 对remoteDeleteFilePath判空
     * @return 不为空时，返回true
     */
    public boolean isNotNull()
    {
        if(null == remoteDeleteFilePath)
        {
            logger.error("method :isNotNull(): remoteDeleteFilePath is null,stop to delete");
            return false;
        }
        return true;
    }

    public String getRemoteDeleteFilePath()
    {
        return remoteDeleteFilePath;
    }

    public void setRemoteDeleteFilePath(String remoteDeleteFilePath)
    {
        this.remoteDeleteFilePath = remoteDeleteFilePath;
    }
}
