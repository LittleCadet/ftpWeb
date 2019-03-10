package ftpTest;

import base.Base;
import com.myproj.entity.*;
import com.myproj.ftp.FtpUpload;
import com.myproj.service.DeleteService;
import com.myproj.service.DownloadService;
import com.myproj.service.ScanService;
import com.myproj.service.UploadServcie;
import com.myproj.tools.FtpUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public class FtpServiceTest extends Base
{
   /* @Autowired
    private UploadServcie uploadServcie;

    @Autowired
    //private DownloadService downloadService;*/

    private String host = "47.99.112.38";

    private String account = "test2";

    private String password = "test";

    private String userId = "666";

    private Boolean scan_switch = false;

    @Test
    public void upload()
    {
        Upload upload = new Upload();
        upload.setAccount(account);
        upload.setPassword(password);
        upload.setHost(host);
        upload.setUserId(userId);
        upload.setLocalUploadFilePath("D:\\fxDownload\\ftpTest\\ftpTest.zip");
        upload.setRemoteUploadFilePath("/usr/test/ftpTest");
        UploadServcie uploadServcie = (UploadServcie)context.getBean("uploadService");
        Assert.assertEquals(1, uploadServcie.insert(upload));
    }

    @Test
    public void download()
    {
        Download download = new Download();
        download.setAccount(account);
        download.setPassword(password);
        download.setHost(host);
        download.setUserId(userId);
        download.setLocalDownloadFilePath("/usr/edward/ftpTest/ftpTest.zip");
        download.setRemoteDownloadFilePath("D:\\test\\ftpTest2");
        DownloadService downloadService = (DownloadService)context.getBean("downloadService");
        Assert.assertEquals(1, downloadService.insert(download));
    }

    @Test
    public void delete()
    {
        Delete delete = new Delete();
        delete.setAccount(account);
        delete.setHost(host);
        delete.setPassword(password);
        delete.setUserId(userId);
        delete.setRemoteDeleteFilePath("/usr/test/ftpTest/ftpTest.zip");
        DeleteService deleteService = (DeleteService)context.getBean("deleteService");
        Assert.assertEquals(1, deleteService.insert(delete));
    }

    @Test
    public void scan()
    {
        if (scan_switch)
        {
            Scan scan = new Scan();
            scan.setAccount(account);
            scan.setHost(host);
            scan.setPassword(password);
            scan.setUserId(userId);
            scan.setLocalDownloadFilePath("D:\\test\\ftpTest2");
            scan.setRemoteScanFilePath("/usr/edward/ftpTest/ftpTest.zip");

            Upload upload = new Upload();
            upload.setAccount(account);
            upload.setPassword(password);
            upload.setHost(host);
            upload.setUserId(userId);
            upload.setLocalUploadFilePath("D:\\fxDownload\\ftpTest\\ftpTest.zip");
            upload.setRemoteUploadFilePath("/usr/test/ftpTest");

            /*ScanService scanService = (ScanService)context.getBean("scanService");
            Assert.assertEquals(1,scanService.insert(scan));*/

            System.out.println("-------------当前的时间是：" + new Date() + "------------------");
            try
            {
                //第5秒有下载任务【download】，刚开始运行时有下载任务[scanDirectory]
                Thread.sleep(20000);

                System.out.println("-------------开始执行文件上传操作-------------");

                //更新服务器上的文件的时间
                UploadServcie uploadServcie = (UploadServcie)context.getBean("uploadService");
                Assert.assertEquals(1, uploadServcie.insert(upload));

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
}
