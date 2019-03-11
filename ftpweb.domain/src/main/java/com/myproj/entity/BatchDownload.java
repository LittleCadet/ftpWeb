package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author LettleCadet
 * @Date 2019/2/26
 */
public class BatchDownload extends FtpService
{
    //ftp本地下载路径
    @NotBlank
    private String localDownloadFilePath;

    //ftp批量下载路径
    @NotBlank
    private String batchDownloadFilePath;

    public BatchDownload()
    {
    }

    public BatchDownload(@NotBlank String localDownloadFilePath,
        @NotBlank String batchDownloadFilePath)
    {
        this.localDownloadFilePath = localDownloadFilePath;
        this.batchDownloadFilePath = batchDownloadFilePath;
    }

    public String getLocalDownloadFilePath()
    {
        return localDownloadFilePath;
    }

    public void setLocalDownloadFilePath(String localDownloadFilePath)
    {
        this.localDownloadFilePath = localDownloadFilePath;
    }

    public String getBatchDownloadFilePath()
    {
        return batchDownloadFilePath;
    }

    public void setBatchDownloadFilePath(String batchDownloadFilePath)
    {
        this.batchDownloadFilePath = batchDownloadFilePath;
    }

    @Override
    public String toString()
    {
        return super.toString() + ",BatchDownload{" +
            "localDownloadFilePath='" + localDownloadFilePath + '\'' +
            ", batchDownloadFilePath='" + batchDownloadFilePath + '\'' +
            '}';
    }
}
