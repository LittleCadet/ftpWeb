package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * LittleCadet
 * 2019/2/26
 **/
public class Download extends FtpService
{
    //ftp远程下载路径
    @NotBlank
    private String remoteDownloadFilePath;

    //ftp本地下载路径
    @NotBlank
    private String localDownloadFilePath;

    public Download()
    {
    }

    public Download(@NotBlank String remoteDownloadFilePath,
        @NotBlank String localDownloadFilePath)
    {
        this.remoteDownloadFilePath = remoteDownloadFilePath;
        this.localDownloadFilePath = localDownloadFilePath;
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

    @Override
    public String toString()
    {
        return "Download{" +
            "remoteDownloadFilePath='" + remoteDownloadFilePath + '\'' +
            ", localDownloadFilePath='" + localDownloadFilePath + '\'' +
            '}';
    }
}
