package com.myproj.ftp;

import com.myproj.tools.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

/**
 * ftp的删除操作
 * @Author 沈燮
 * @Date 2018/12/27
 */
public class FtpDelete
{
    //指定删除服务器的路径
    private String remoteDeleteFilePath;

    //分隔符“/”
    private final String SPLIT_FORWARD_SLASH = "/";

    private FTPClient client = FtpUtil.init();

    /**
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
                System.out.println("因该文件上级目录不存在，所以该文件不存在，无需删除");
                return true;
            }
        }
        catch (IOException e)
        {
            System.out.println("something wrong with changing directory by ftp：exception:"+e);

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

        System.out.println("开始删除指定文件：指定文件路径："+ remoteDeleteFilePath +",文件名称："+fileName);


        // 检验文件夹是否存在:通过删除该文件的方式来判定，如果返回码为250，则删除该文件成功，那么代表该文件夹存在,返回码为550，代表删除失败
        try
        {
            isExist = client.dele(fileName);

            System.out.println("该文件在服务器中" +(isExist==250?"删除成功":"不存在")+",文件名称:" + fileName);
        }
        catch (IOException e)
        {
            System.out.println("failed to delete file by ftp ：remoteDeleteFilePath："+remoteDeleteFilePath+",exception:"+e);

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
            System.out.println("remoteDeleteFilePath为空，删除操作停止");
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
