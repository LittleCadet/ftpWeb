package com.myproj.domain;

import com.myproj.domain.init.InitSpring;
import com.myproj.ftp.FtpUpload;
import org.springframework.context.ApplicationContext;

import java.util.Date;

/**
 * Ftp的实体类
 * 沈燮
 * 2019/1/3
 **/
public class Ftp
{
    public static void main(String[] args)
    {
        try
        {
            ApplicationContext context = InitSpring.init();

            System.out.println("--------------北京时间:"+ new Date() +"--------------");
            //第5秒有下载任务【download】，刚开始运行时有下载任务[scanDirectory]
            Thread.sleep(20000);

            System.out.println("-------------开始执行文件上传操作-------------");

            //更新服务器上的文件的时间
            FtpUpload upload = (FtpUpload)context.getBean("ftpUpload");
            upload.upload();

            System.out.println("-------------文件上传完成的时间是：" + new Date() + "------------------");

            //会立刻执行一次下载任务
            //Thread.sleep(40000);
        }
        catch (InterruptedException e)
        {
            System.out.println("Thead exception");
        }

    }
}
