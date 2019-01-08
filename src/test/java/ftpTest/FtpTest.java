package ftpTest;

import com.myproj.ftp.*;
import init.InitSpring;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Date;

/**
 * ftp功能测试类
 *
 * @Author 沈燮
 * @Date 2018/12/27
 */
public class FtpTest
{
    private static ApplicationContext context = InitSpring.init();

    /**
     * 用ftp上传到服务器上指定文件到指定目录下
     */
    @Test
    public void upload()
    {
        FtpUpload ftpUpload = (FtpUpload)context.getBean("ftpUpload");
        Assert.assertTrue(ftpUpload.upload());
    }

    /**
     * 用ftp删除服务器上指定路径下的指定文件夹
     */
    @Test
    public void deleteFile()
    {
        FtpDelete ftpDelete = (FtpDelete)context.getBean("ftpDelete");
        Assert.assertTrue(ftpDelete.deleteFile());
    }

    /**
     * 用ftp从服务器下载指定文件
     */
    @Test
    public void download()
    {
        FtpDownload ftpDownload = (FtpDownload)context.getBean("ftpDownload");
        Assert.assertTrue(ftpDownload.download());
    }

    /**
     * 批量上传到服务器
     */
    @Test
    public void batchUpload()
    {
        FtpBatchUpload ftpBatchUpload = (FtpBatchUpload)context.getBean("ftpBatchUpload");
        Assert.assertTrue(ftpBatchUpload.batchUpload());
    }

    /**
     * 批量从服务器下载到本地
     */
    @Test
    public void batchDownload()
    {
        FtpBatchDownload ftpBatchDownload = (FtpBatchDownload)context.getBean("ftpBatchDownload");
        Assert.assertTrue(ftpBatchDownload.batchDownload());
    }

    /**
     * 批量删除服务器上的文件
     */
    @Test
    public void batchDelete()
    {
        FtpBatchDelete ftpBatchDelete = (FtpBatchDelete)context.getBean("ftpBatchDelete");
        Assert.assertTrue(ftpBatchDelete.batchDeleteFile());
    }

    /**
     * 测试定时下载：让线程睡眠的目的：让spring容器在睡眠的时间中保持唤醒的状态
     */
    @Test
    public void scheduleDownload()
    {
        System.out.println("-------------当前的时间是：" + new Date() + "------------------");
        try
        {
            Thread.sleep(1 * 60000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 测试定时扫描：让线程睡眠的目的：让spring容器在睡眠的时间中保持唤醒的状态
     */
    @Test
    public void scheduleScan()
    {
        try
        {
            System.out.println();

            //20秒内只能有一次下载动作
            Thread.sleep(20000);

            System.out.println("-------------开始执行文件上传操作-------------");

            //更新服务器上的文件的时间
            upload();

            System.out.println("-------------文件上传完成的时间是：" + new Date() + "------------------");

            //开始执行下载任务，但只有一次下载
            Thread.sleep(30000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 运行两个定时任务：定时下载+定时扫描
     */
    @Test
    public void scheduleBoth()
    {
        System.out.println("-------------当前的时间是：" + new Date() + "------------------");
        try
        {
            //第5秒有下载任务【download】，刚开始运行时有下载任务[scanDirectory]
            Thread.sleep(20000);

            System.out.println("-------------开始执行文件上传操作-------------");

            //更新服务器上的文件的时间
            upload();

            System.out.println("-------------文件上传完成的时间是：" + new Date() + "------------------");

            //会立刻执行一次下载任务
            Thread.sleep(40000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
