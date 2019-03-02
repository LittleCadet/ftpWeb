package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author LettleCadet
 * @Date 2019/2/26
 */
public class BatchUpload extends FtpService
{
    //ftp本地上传路径
    @NotBlank
    private String localUploadFilePath;

    //ftp批量上传路径
    @NotBlank
    private String batchUploadFilePath;

    public BatchUpload()
    {
    }

    public BatchUpload(@NotBlank String localUploadFilePath,
        @NotBlank String batchUploadFilePath)
    {
        this.localUploadFilePath = localUploadFilePath;
        this.batchUploadFilePath = batchUploadFilePath;
    }

    public String getLocalUploadFilePath()
    {
        return localUploadFilePath;
    }

    public void setLocalUploadFilePath(String localUploadFilePath)
    {
        this.localUploadFilePath = localUploadFilePath;
    }

    public String getBatchUploadFilePath()
    {
        return batchUploadFilePath;
    }

    public void setBatchUploadFilePath(String batchUploadFilePath)
    {
        this.batchUploadFilePath = batchUploadFilePath;
    }

    @Override
    public String toString()
    {
        return "BatchUpload{" +
            "localUploadFilePath='" + localUploadFilePath + '\'' +
            ", batchUploadFilePath='" + batchUploadFilePath + '\'' +
            '}';
    }
}
