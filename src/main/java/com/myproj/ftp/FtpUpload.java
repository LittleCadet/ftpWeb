package com.myproj.ftp;

import com.myproj.tools.FtpUtil;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * ftp上传操作
 * @author： 沈燮
 * @CreateDate:2018/12/21
 */
public class FtpUpload
{

    private FTPClient client = FtpUtil.init();

    //指定上传到服务器的路径
    private String remoteUploadFilePath;

    //指定需要上传的本地文件路径
    private String localUploadFilePath;

     //分隔符‘\’
     private final String SPLIT_BACKSLASH = "\\";

     //分隔符“/”
    private final String SPLIT_FORWARD_SLASH = "/";

    /**
     * ftp上传到指定服务器：根据ip和端口号连接到ftp，根据用户名，密码登录ftp，将二进制文件上到ftp（3次机会），关流，注销ftp用户，ftp断开连接
     * @return 判定是否上传完成
     */
    public Boolean upload()
    {
        Boolean flag = false;

        if(isNotNull())
        {
            if (FtpUtil.connectToFtp())
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

            System.out.println("正在用ftp上传指定文件到服务器");

            //用ftpClient上传到服务器
            flag = client.storeFile(remote, is);

            System.out.println("用ftp传输二进制文件" +(flag.toString().equals("true") ?"成功":"失败"));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("uploadFileToFtp:failed,localUploadFilePath:"+localUploadFilePath+",\nexception:"+e);

            return false;
        }
        catch (IOException e)
        {
            System.out.println("uploadFileToFtp:failed"+",\nexception:"+e);

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
                System.out.println("正在用ftp在服务器上创建目录：");

                //在shell根目录下创建文件夹
                flag = client.makeDirectory(packageName);

                System.out.println("在服务器创建文件夹" +(flag.toString().equals("true") ?"成功":"失败")+",文件夹名称:" + packageName);
            }
            else
            {
                System.out.println("在服务器已存在该文件夹！");
            }
        }
        catch (IOException e)
        {
            System.out.println("mkDir:fail,remoteUploadFilePath:"+remoteUploadFilePath+",\nexception:"+e);
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
            System.out.println("remoteUploadFilePath为空，上传操作停止");
            return false;
        }

        if(null == localUploadFilePath)
        {
            System.out.println("localUploadFilePath为空，上传操作停止");
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






