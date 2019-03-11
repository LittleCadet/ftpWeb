package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author LettleCadet
 * @Date 2019/2/26
 */
public class Scan extends FtpService
{
    //ftp本地下载路径
    @NotBlank
    private String localDownloadFilePath;

    //ftp远程扫描路径
    @NotBlank
    private String remoteScanFilePath;

    public Scan()
    {
    }

    public Scan(@NotBlank String localDownloadFilePath,
        @NotBlank String remoteScanFilePath)
    {
        this.localDownloadFilePath = localDownloadFilePath;
        this.remoteScanFilePath = remoteScanFilePath;
    }

    public String getLocalDownloadFilePath()
    {
        return localDownloadFilePath;
    }

    public void setLocalDownloadFilePath(String localDownloadFilePath)
    {
        this.localDownloadFilePath = localDownloadFilePath;
    }

    public String getRemoteScanFilePath()
    {
        return remoteScanFilePath;
    }

    public void setRemoteScanFilePath(String remoteScanFilePath)
    {
        this.remoteScanFilePath = remoteScanFilePath;
    }

    @Override
    public String toString()
    {
        return super.toString() + ",Scan{" +
            "localDownloadFilePath='" + localDownloadFilePath + '\'' +
            ", remoteScanFilePath='" + remoteScanFilePath + '\'' +
            '}';
    }
}
