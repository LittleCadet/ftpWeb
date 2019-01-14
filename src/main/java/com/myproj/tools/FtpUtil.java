package com.myproj.tools;

import com.myproj.ftp.FtpUpload;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ftp的工具类
 * @Author 沈燮
 * @Date 2018/12/27
 */
public class FtpUtil
{
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class.getName());

    //远程主机ip
    private static String host;

    //远程主机用户名
    private static String account;

    //远程主机密码
    private static String password;

    //重试次数
    private static String reTryTimes;

    //ftp的socket的失效时间
    private static String timeOut;

    //FTPClient的对象
    private static FTPClient client = null;

    public static FTPClient init()
    {
        if(null == client)
        {
            return client = new FTPClient();
        }
        return client;

    }

    /**
     * 连接ftp并登录,配有重试机制，设置socket的过期时间
     * @return 是否连接成功
     */
    public static boolean connectToFtp()
    {
        for(int i = 0;i< Integer.valueOf(reTryTimes);i++)
        {
            if(connectToFtpProcess())
            {
                return true;
            }

            logger.error("method:FtpUtil.connectToFtp():failure times :" + (i+1) +",host is " + host);
        }

        return false;
    }

    public static boolean connectToFtpProcess()
    {
        {
            Boolean flag = true;
            try
            {
                if(!client.isConnected())
                {
                    //根据ip连接ftp
                    client.connect(host);

                    if(client.isConnected())
                    {
                        //登录ftp
                        flag = client.login(account,password);
                    }

                    if(logger.isDebugEnabled())
                    {
                        logger.debug("method:FtpUtil.connectToFtpProcess():login " +(flag.toString().equals("true") ? "seccuss!" : "fail!"));
                    }
                }

                if(!flag)
                {
                    //只要登录失败，就断开ftp连接
                    client.disconnect();

                    logger.error("method:FtpUtil.connectToFtpProcess():login failed:userName:" +account + ",password:"+password );

                    return false;
                }

                //设置socket的过期时间
                client.setSoTimeout(Integer.valueOf(timeOut));


            }
            catch (IOException e)
            {
                logger.error("method:FtpUtil.connectToFtpProcess(): connectToFtp, failed,userName:" + account + ",password:"+password+",\nexception:"+e );
                return false;
            }
            return true;
        }
    }

    /**
     * 关闭is,注销ftp用户，断开ftp
     * @param is 输入流
     * @param client ftp客户端
     */
    public static void closeResources(InputStream is, FileOutputStream os,FTPClient client)
    {
        try
        {
            if(null != is)
            {
                is.close();
            }

            if(null != os)
            {
                os.close();
            }

            if(client.isConnected())
            {

                //注销用户
                client.logout();

                if(logger.isDebugEnabled())
                {
                    logger.debug("method:FtpUtil.closeResources(): user was logout");
                }

                //ftpClient断开连接
                client.disconnect();

                if(logger.isDebugEnabled())
                {
                    logger.debug("method:FtpUtil.closeResources(): ftp was disconnected");
                }
            }
        }
        catch (IOException e)
        {
            logger.error("method: Ftputil.closeResources:closeResources:fail,\n the status of ftpClient："+(String.valueOf(client.isConnected()).equals(Boolean.TRUE)?"connect":"disconnect"+ ",\nexception:"+e));
        }
    }

    public static void setHost(String host)
    {
        FtpUtil.host = host;
    }

    public static String getHost()
    {
        return host;
    }

    public static void setAccount(String account)
    {
        FtpUtil.account = account;
    }

    public static String getAccount()
    {
        return account;
    }

    public static String getPassword()
    {
        return password;
    }

    public static void setPassword(String password)
    {
        FtpUtil.password = password;
    }

    public static String getReTryTimes()
    {
        return reTryTimes;
    }

    public static void setReTryTimes(String reTryTimes)
    {
        FtpUtil.reTryTimes = reTryTimes;
    }

    public static String getTimeOut()
    {
        return timeOut;
    }

    public static void setTimeOut(String timeOut)
    {
        FtpUtil.timeOut = timeOut;
    }

    public static FTPClient getClient()
    {
        return client;
    }

    public static void setClient(FTPClient client)
    {
        FtpUtil.client = client;
    }
}
