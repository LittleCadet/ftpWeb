package com.myproj.ftp;

import com.myproj.constants.FtpConstants;
import com.myproj.tools.FtpUtil;
import com.myproj.tools.IsLinuxUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * ftp的下载操作
 * @Author LettleCadet
 * @Date 2018/12/27
 */
public class FtpDownload
{
    private static final Logger logger = LoggerFactory.getLogger(FtpDownload.class.getName());

    private FTPClient client = FtpUtil.init();

    //指定下载的服务器的路径
    private String remoteDownloadFilePath;

    //指定需要下载到本地文件路径
    private String localDownloadFilePath;

    //分隔符‘\’
    private final String SPLIT_BACKSLASH = "\\";

    //分隔符“/”
    private final String SPLIT_FORWARD_SLASH = "/";

    //计数：ftp下载动作一共执行多少次
    private Integer count = 0;

    //windows的开关，1：关闭，0：开启
    private Integer windows_switch = 1;

    /**
     * 供定时任务使用
     * 下载文件
     * @return 判定是否下载完成
     */
    public boolean download()
    {
        boolean flag = false;

        if(isNotNull())
        {
            //建立ftp连接
            if(FtpUtil.connectToFtp())
            {
                //下载操作
                flag = downloadProcess();

                count ++;
            }
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("------------downloaded file in" + new Date() + "------------");
            logger.debug("------------downloaded times:  " + count + "  times------------");
            logger.debug("method: download() was exited");
        }
        return flag;

    }

    /**
     * 下载文件
     * @return 判定是否下载完成
     */
    public boolean download(String remoteDownloadFilePath,String localDownloadFilePath,Integer windowsSwitch)
    {
        boolean flag = false;

        this.windows_switch = windowsSwitch;
        this.remoteDownloadFilePath = remoteDownloadFilePath;
        this.localDownloadFilePath = localDownloadFilePath;

        if(isNotNull())
        {
            //建立ftp连接
            if(FtpUtil.connectToFtp())
            {
                //下载操作
                flag = downloadProcess();

                count ++;
            }
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("------------downloaded file in" + new Date() + "------------");
            logger.debug("------------downloaded times:  " + count + "  times------------");
            logger.debug("method: download() was exited");
        }
        return flag;

    }

    /**
     * 下载的具体操作
     * @return  判定是否下载完成
     */
    public boolean downloadProcess()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("method : downloadProcess(): localDownloadFilePath :" + localDownloadFilePath + ",remoteDownloadFilePath:" +remoteDownloadFilePath);
        }

        Boolean flag = false;

        FileOutputStream os = null;

        String localDownloadFilePath = null;
        try
        {
            String fileName = new File(remoteDownloadFilePath).getName();

            //判定本地目录是否存在,不存在，则创建目录
            localDownloadFilePath = isLocalDir();

            //输出流必须精确到文件：如果精确到目录，会报fileNotFound Exception
            os = new FileOutputStream(localDownloadFilePath);

            //linux设置本地被动模式，在windows默认打开
            client.enterLocalPassiveMode();

            //设置ftp专用的二进制码
            client.setFileType(FTPClient.BINARY_FILE_TYPE);

            //当指定的文件在服务器上不存在时，终止下载操作
            if(!isRemoteDir())
            {
                logger.error("method : downloadProcess():file not exist,stop download");

                //关闭资源
                FtpUtil.closeResources(null, os, client);

                return false;
            }

            if(logger.isDebugEnabled())
            {
                logger.debug("method : downloadProcess():ftp download is starting");
            }

            //下载操作
            flag = client.retrieveFile(fileName,os);


            if(logger.isDebugEnabled())
            {
                logger.debug("method : downloadProcess():ftp download" +(String.valueOf(flag).equals("true")?"success!":"fail!"));
            }
        }
        catch (IOException e)
        {
            logger.error("method : downloadProcess():something wrong with io exception,exception:"+e + "localDownloadFilePath:"+localDownloadFilePath);

            return false;
        }
        finally
        {
            //关闭资源
            FtpUtil.closeResources(null, os, client);
        }

        return flag;
    }

    /**
     * 判定服务器的文件是否是否存在
     * @return 判定服务器的文件是否是否存在
     */
    public boolean isRemoteDir()
    {
        int remoteIndex = remoteDownloadFilePath.lastIndexOf(SPLIT_FORWARD_SLASH);
        String remoteDir = remoteDownloadFilePath.substring(0,remoteIndex);
        String fileName = new File(remoteDownloadFilePath).getName();
        try
        {
            //判定目录是否存在
            if(!client.changeWorkingDirectory(remoteDir))
            {

                logger.error("method : isRemoteDir():file not exist,stop download");

                //关闭资源
                FtpUtil.closeResources(null, null, client);

                return false;
            }

            FTPFile[] ftpFiles = client.listFiles();

            for(FTPFile file : ftpFiles)
            {
                if(fileName.equals(file.getName()))
                {
                    return true;
                }
            }
        }
        catch (IOException e)
        {
            logger.error("method : isRemoteDir():something wrong in ftp operation ,exception:" + e);

            return false;
        }

        return false;
    }

    /**
     * 判定本地目录是否存在
     * @return 判定本地目录是否存在
     */
    public String isLocalDir()
    {
        File localFile = new File(localDownloadFilePath);

        File remoteFile = new File(remoteDownloadFilePath);

        String localDownloadFilePath = this.localDownloadFilePath;

        if (!localFile.exists())
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("method : isLocalDir():create local directory" + (String.valueOf(localFile.mkdir()).equals("true") ? "success!" : "fail!") );
            }
        }

        //本地测试，用‘\\’，服务器跑定时任务，用‘/’
        if (IsLinuxUtil.isWindows() || !FtpConstants.WINDOWS_SWITCH.equals(windows_switch))
        {
            if (!SPLIT_BACKSLASH.equals(localDownloadFilePath.substring(localDownloadFilePath.lastIndexOf(
                SPLIT_BACKSLASH))))
            {
                localDownloadFilePath = this.localDownloadFilePath + SPLIT_BACKSLASH;
            }
        }
        else
        {
            if (!SPLIT_FORWARD_SLASH.equals(localDownloadFilePath.substring(localDownloadFilePath.lastIndexOf(
                SPLIT_FORWARD_SLASH))))
            {
                localDownloadFilePath = this.localDownloadFilePath + SPLIT_FORWARD_SLASH;
            }
        }

        //拼接本地路径，否则在创建输出流时，会报文件找不到异常
        localDownloadFilePath = localDownloadFilePath + remoteFile.getName();

        return localDownloadFilePath;
    }

    /**
     * 对remoteDownloadFilePath，localDownloadFilePath判空
     * @return 都不为空时，返回true
     */
    public boolean isNotNull()
    {
        if(null == remoteDownloadFilePath)
        {
            logger.error("method :isNotNull()：remoteDownloadFilePath is null , stop download");
            return false;
        }

        if(null == localDownloadFilePath)
        {
            logger.error("method :isNotNull():localDownloadFilePath is null , stop download");
            return false;
        }
        return true;
    }

    public String getRemoteDownloadFilePath()
    {
        return remoteDownloadFilePath;
    }

    public void setRemoteDownloadFilePath(String remoteDownloadFilePath)
    {
        this.remoteDownloadFilePath = remoteDownloadFilePath;
    }

    public String getLocalDownloadFilePath()
    {
        return localDownloadFilePath;
    }

    public void setLocalDownloadFilePath(String localDownloadFilePath)
    {
        this.localDownloadFilePath = localDownloadFilePath;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }
}
