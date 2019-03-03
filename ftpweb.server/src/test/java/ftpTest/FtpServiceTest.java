package ftpTest;

import base.Base;
import com.myproj.entity.Download;
import com.myproj.entity.FtpService;
import com.myproj.entity.Upload;
import com.myproj.ftp.FtpUpload;
import com.myproj.service.DownloadService;
import com.myproj.service.UploadServcie;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public class FtpServiceTest extends Base
{
    /*@Autowired
    private UploadServcie uploadServcie;*/

    @Autowired
    private DownloadService downloadService;

    private String host = "47.99.112.38";

    private String account = "test";

    private String password = "test";

    private String userId = "666";

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
        Assert.assertEquals(1,uploadServcie.insert(upload));
    }

    @Test
    public void download()
    {
        Download download = new Download();
        download.setAccount(account);
        download.setPassword(password);
        download.setHost(host);
        download.setLocalDownloadFilePath("/usr/edward/ftpTest/ftpTest.zip");
        download.setRemoteDownloadFilePath("D:\\test\\ftpTest2");
        //DownloadService downloadService = (DownloadService)context.getBean("downloadService");
        Assert.assertEquals(1,downloadService.insert(download));
    }
}
