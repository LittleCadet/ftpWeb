package com.myproj.ftp;

import com.myproj.tools.FtpUtil;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

/**
 * ftp上传操作
 * @author： 沈燮
 * @CreateDate:2018/12/21
 */
public class FtpUpload
{
    private static final Logger logger = LoggerFactory.getLogger(FtpUpload.class.getName());

    private FTPClient client = FtpUtil.init();

    //指定上传到服务器的路径
    private String remoteUploadFilePath;

    //指定需要上传的本地文件路径
    private String localUploadFilePath;

     //分隔符‘\’
     private final String SPLIT_BACKSLASH = "\\";

     //分隔符“/”
    private final String SPLIT_FORWARD_SLASH = "/";

    //记录上传次数
    private Integer count = 0;

    /**
     * ftp上传到指定服务器：根据ip和端口号连接到ftp，根据用户名，密码登录ftp，将二进制文件上到ftp（3次机会），关流，注销ftp用户，ftp断开连接
     * @return 判定是否上传完成
     */
    public Boolean upload()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("method: FtpUpload.upload() is entering");
        }
        Boolean flag = false;

        if(isNotNull())
        {
            if (FtpUtil.connectToFtp())
            {
                //开始上传操作
                flag = uploadFileToFtp();

                count ++;
            }
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("------------uploaded file in" + new Date() + "------------");
            logger.debug("------------uploaded times are" + count + "times------------");
            logger.debug("method: upload() was existed");
        }
        return flag;
    }

    /**
     * 创建服务器文件，将二进制的文件上传到ftp
     * @return 判定是否上传成功
     */
    public boolean uploadFileToFtp()
    {
        Boolean flag = false;
        InputStream is = null;
        try
        {
            //用io去读本地文件
            is = new FileInputStream(localUploadFilePath);

            String fileName = new File(localUploadFilePath).getName();

            //在远程服务器创建文件
            mkDir();

            //linux开启本地被动模式，windows会默认自动开启
            client.enterLocalPassiveMode();

            //设置二进制
            client.setFileType(FTP.BINARY_FILE_TYPE);

            //获得服务器的文本编码格式
            String controlEncoding = client.getControlEncoding();

            //将文件名由utf-8转化为ftp的文字编码格式
            fileName = new String(fileName.getBytes("UTF-8"),controlEncoding);

            // ftp路径:路径必须具体到文件，否则上传不成功
            String remote = (!remoteUploadFilePath.endsWith(SPLIT_FORWARD_SLASH) ? remoteUploadFilePath + SPLIT_FORWARD_SLASH : remoteUploadFilePath) + fileName;

            if(logger.isDebugEnabled())
            {
                logger.debug("method: uploadFileToFtp():ftp is uploading thr file to server");
            }

            //用ftpClient上传到服务器
            flag = client.storeFile(remote, is);

            if(logger.isDebugEnabled())
            {
                logger.debug("method:uploadFileToFtp(): binary file uploaded " + (flag.toString().equals("true") ?"成功":"失败"));
            }
        }
        catch (FileNotFoundException e)
        {
            logger.error("method: uploadFileToFtp(): uploadFileToFtp:failed,localUploadFilePath:"+localUploadFilePath+",\nexception:"+e);

            return false;
        }
        catch (IOException e)
        {
            logger.error("method: uploadFileToFtp(): uploadFileToFtp:failed"+",\nexception:"+e);

            return false;
        }
        finally
        {
            //关闭资源
            FtpUtil.closeResources(is,null,client);
        }


        return flag;
    }

    /**
     * 判定是否在服务器创建文件成功
     */
    public void mkDir()
    {
        Boolean flag = Boolean.FALSE;

        try
        {
            int index = remoteUploadFilePath.lastIndexOf(SPLIT_FORWARD_SLASH);

            //去除“/”
            String packageName = remoteUploadFilePath.substring(index+1);

            //如果文件不存在，则创建目录
            if (!client.changeWorkingDirectory(remoteUploadFilePath) )
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("method :mkDir(): it is creating directory");
                }

                //在shell根目录下创建文件夹
                flag = client.makeDirectory(packageName);

                if(logger.isDebugEnabled())
                {
                    logger.debug("method :mkDir():creating deirectory was " + (flag.toString().equals("true") ?"successed!":"failed !") + ",directory name is " + packageName);
                }
            }
            else
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("method :mkDir(): the directory is existed");
                }
            }
        }
        catch (IOException e)
        {
            logger.error("mthod:mkDir:fail,remoteUploadFilePath:"+remoteUploadFilePath+",\nexception:"+e);
        }
    }

    /**
     * 对remoteUploadFilePath，localUploadFilePath判空
     * @return 都不为空时，返回true
     */
    public boolean isNotNull()
    {
        if(null == remoteUploadFilePath)
        {
            logger.error("method:isNotNull():remoteUploadFilePath is null,operation of uploading is stopping");
            return false;
        }

        if(null == localUploadFilePath)
        {
            logger.error("method:isNotNull():localUploadFilePath is null,operation of uploading is stopping");
            return false;
        }
        return true;
    }

    public String getRemoteUploadFilePath()
    {
        return remoteUploadFilePath;
    }

    public void setRemoteUploadFilePath(String remoteUploadFilePath)
    {
        this.remoteUploadFilePath = remoteUploadFilePath;
    }

    public String getLocalUploadFilePath()
    {
        return localUploadFilePath;
    }

    public void setLocalUploadFilePath(String localUploadFilePath)
    {
        this.localUploadFilePath = localUploadFilePath;
    }
}






