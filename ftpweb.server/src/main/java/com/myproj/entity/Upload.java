package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author LettleCadet
 * @Date 2019/2/26
 */
public class Upload extends FtpService
{
    //ftp远程上传路径
    @NotBlank
    private String remoteUploadFilePath;

    //ftp本地上传路径
    @NotBlank
    private String localUploadFilePath;

    public Upload()
    {
    }

    public Upload(@NotBlank String remoteUploadFilePath,
        @NotBlank String localUploadFilePath)
    {
        this.remoteUploadFilePath = remoteUploadFilePath;
        this.localUploadFilePath = localUploadFilePath;
    }

    public String getRemoteUploadFilePath()
    {
        return remoteUploadFilePath;
    }

    public void setRemoteUploadFilePath(String remoteUploadFilePath)
    {
        this.remoteUploadFilePath = remoteUploadFilePath;
    }

    public String getLocalUploadFilePath()
    {
        return localUploadFilePath;
    }

    public void setLocalUploadFilePath(String localUploadFilePath)
    {
        this.localUploadFilePath = localUploadFilePath;
    }

    @Override
    public String toString()
    {
        return "Upload{" +
            "remoteUploadFilePath='" + remoteUploadFilePath + '\'' +
            ", localUploadFilePath='" + localUploadFilePath + '\'' +
            '}';
    }
}
