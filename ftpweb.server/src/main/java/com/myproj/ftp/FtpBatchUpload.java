package com.myproj.ftp;

import com.myproj.tools.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

/**
 * ftp的批量删除，支持不同目录下的文件上传到指定目录
 * LettleCadet
 * 2019/1/3
 **/
public class FtpBatchUpload
{
    private static final Logger logger = LoggerFactory.getLogger(FtpBatchUpload.class.getName());

    private FTPClient client = FtpUtil.init();

    //指定上传到服务器的路径
    private Map<String,String> filePaths = new HashMap<String,String>();

    //存放上传失败的文件的路径
    private Map<String,String> fails = new HashMap<String,String>();

    //分隔符‘\’
    private final String SPLIT_BACKSLASH = "\\";

    //分隔符“/”
    private final String SPLIT_FORWARD_SLASH = "/";

    //是文件的标志“.”
    private final String IS_FILE = ".";

    //批量上传的文本路径
    private String batchUploadFilePath;

    /**
     * ftp上传到指定服务器：根据ip和端口号连接到ftp，根据用户名，密码登录ftp，将二进制文件上到ftp（3次机会），关流，注销ftp用户，ftp断开连接
     * @return 判定是否上传完成
     */
    public Boolean batchUpload()
    {
        Boolean flag = false;

        //对txt数据预处理，如本地或者服务器路径缺失，会即时会退出批量上传操作
        if(processData())
        {
            if(FtpUtil.connectToFtp())
            {
                //开始上传操作
                flag = uploadFileToFtp();
            }
        }
        return flag;
    }

    /**
     * 创建服务器文件，将二进制的文件上传到ftp
     * @return 判定是否上传成功
     */
    public boolean uploadFileToFtp()
    {
        int count = 0;
        Boolean flag = false;
        FileInputStream is = null;
        String localUploadFilePath = null;
        String remoteUploadFilePath = null;
        List<Boolean> result = new ArrayList<Boolean>();

        try
        {
            for (Map.Entry<String, String> entry : filePaths.entrySet())
            {
                localUploadFilePath = entry.getKey();
                remoteUploadFilePath = entry.getValue();

                int index = localUploadFilePath.lastIndexOf(SPLIT_BACKSLASH);

                //获取最后一个“\\”之后的文件名
                String lastFileName = localUploadFilePath.substring(index+1);

                //如果localUploadFilePath是目录，则需要获取到所有该目录下的文件，之后上传到指定的ftp的目录
                if (!lastFileName.contains(IS_FILE))
                {
                    File file = new File(localUploadFilePath);
                    File[] files = file.listFiles();
                    for (File localFile : files)
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("method:uploadFileToFtp():localUploadFilePath:" + localFile.toString() + "\n" + "remoteUploadFilePath:" + remoteUploadFilePath);
                        }
                        //上传细节处理
                        result = uploadProcess(localFile.toString(), remoteUploadFilePath, result,is);
                    }
                }
                else
                {
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("method:uploadFileToFtp():localUploadFilePath:" + localUploadFilePath + "\n" + "remoteUploadFilePath:" + remoteUploadFilePath);
                    }
                    //上传细节处理
                    //如果localUploadFilePath是文件，则直接上传即可
                    result = uploadProcess(localUploadFilePath, remoteUploadFilePath, result,is);
                }

            }

            if (!CollectionUtils.isEmpty(fails))
            {
                logger.debug("method:uploadFileToFtp():****************failed to upload file path****************");
                for (Map.Entry<String, String> fail : fails.entrySet())
                {
                    logger.error("localUploadFilePath:" + fail.getKey());
                    logger.error("remoteUploadFilePath:" + fail.getValue());
                }
            }
            else
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("method:uploadFileToFtp():--------------all files upload  success--------------");
                }
            }

            if (result.contains(false))
            {
                return false;
            }
        }
        catch (FileNotFoundException e)
        {
            logger.error("method:uploadFileToFtp():uploadFileToFtp:failed,localUploadFilePath:"+localUploadFilePath+",\nexception:"+e);
            return false;
        }
        catch (IOException e)
        {
            logger.error("method:uploadFileToFtp():uploadFileToFtp:failed"+",\nexception:"+e);
            return false;
        }
        finally
        {
            //关闭资源
            FtpUtil.closeResources(is,null,client);
        }

        return true;
    }

    /**
     * 判定是否在服务器创建文件成功
     */
    public void mkDir(String remoteUploadFilePath)
    {
        Boolean flag = false;

        try
        {
            int index = remoteUploadFilePath.lastIndexOf(SPLIT_FORWARD_SLASH);

            //去除“/”
            String packageName = remoteUploadFilePath.substring(index+1);

            //如果文件不存在，则创建目录
            if (!client.changeWorkingDirectory(remoteUploadFilePath) )
            {
                //在shell根目录下创建文件夹:注：一次只能创建一级目录，无法做到同时创建多及目录
                flag = client.makeDirectory(packageName);

                if(logger.isDebugEnabled())
                {
                    logger.debug("method:mkDir():making directory" + (flag.toString().equals("true") ?"success":"fail")+",file name is :" + packageName);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("method:mkDir():fail,remoteUploadFilePath:"+remoteUploadFilePath+",\nexception:"+e);
        }
    }

    /**
     * 对txt文件中的数据进行处理
     */
    public boolean processData()
    {
        BufferedReader br = null;
        try
        {
            String line = null;
            br = new BufferedReader(new FileReader(batchUploadFilePath));

            while(null != (line = br.readLine()))
            {
                String[] paths = line.split(" ");
                List<String> li = new ArrayList<String>();
                for(int i = 0;i<paths.length;i++)
                {
                    if(!("".equals(paths[i])))
                    {
                        li.add(paths[i]);
                    }
                }

                for(String path:li)
                {
                    if (line.contains(SPLIT_BACKSLASH) && line.contains(SPLIT_FORWARD_SLASH) && null != path.trim())
                    {
                        if (null != li.get(0) && null != li.get(1))
                        {
                            filePaths.put(li.get(0).trim(), li.get(1).trim());
                        }
                    }
                    else if(line.contains(SPLIT_BACKSLASH) || line.contains(SPLIT_FORWARD_SLASH))
                    {
                        logger.error("method:processData():data not correct in ftpBatchUpload.txt,please handle it");
                        return false;
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            logger.error("method:processData():processData failed in IO operation,e" + e);
            return false;
        }
        catch (IOException e)
        {
            logger.error("method:processData():something wrong with IO operation,e" + e);
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
                    logger.error("method:processData():io close exception,e:" + e);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 上传的具体细节
     * @param localUploadFilePath 本地文件路径
     * @param remoteUploadFilePath 服务器文件路径
     * @param result 布尔值的list
     * @return 每一个上传的成功与否
     */
    public List<Boolean> uploadProcess(String localUploadFilePath,String remoteUploadFilePath,List<Boolean> result,FileInputStream is)
        throws IOException
    {
        Boolean flag = false;

        //用io去读本地文件
        is = new FileInputStream(localUploadFilePath);

        String fileName = new File(localUploadFilePath).getName();

        //在远程服务器创建文件
        mkDir(remoteUploadFilePath);

        //linux开启本地被动模式，windows会默认自动开启
        client.enterLocalPassiveMode();

        //设置二进制
        client.setFileType(FTP.BINARY_FILE_TYPE);

        //获得服务器的文本编码格式
        String controlEncoding = client.getControlEncoding();

        //将文件名由utf-8转化为ftp的文字编码格式
        fileName = new String(fileName.getBytes("UTF-8"), controlEncoding);

        // ftp路径:路径必须具体到文件，否则上传不成功
        String remote = (!remoteUploadFilePath.endsWith(SPLIT_FORWARD_SLASH) ?
            remoteUploadFilePath + SPLIT_FORWARD_SLASH :
            remoteUploadFilePath) + fileName;

        if(logger.isDebugEnabled())
        {
            logger.debug("method:uploadProcess():file is uploading to server");
        }

        //用ftpClient上传到服务器
        flag = client.storeFile(remote, is);

        if (!flag)
        {
            fails.put(localUploadFilePath, remoteUploadFilePath);
        }

        result.add(flag);

        if(logger.isDebugEnabled())
        {
            logger.debug("method :uploadProcess():upload binary file " + (flag.toString().equals("true") ? "success" : "fail"));
        }
        return result;
    }

    public String getBatchUploadFilePath()
    {
        return batchUploadFilePath;
    }

    public void setBatchUploadFilePath(String batchUploadFilePath)
    {
        this.batchUploadFilePath = batchUploadFilePath;
    }
}
