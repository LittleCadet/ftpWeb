package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author LettleCadet
 * @Date 2019/2/26
 */
public class ScheduleUpload extends FtpService
{
    //ftp远程上传路径
    @NotBlank
    private String remoteScheduleUploadFilePath;

    //ftp本地上传路径
    @NotBlank
    private String localUploadFilePath;

    public ScheduleUpload()
    {
    }

    public ScheduleUpload(@NotBlank String remoteScheduleUploadFilePath,
        @NotBlank String localUploadFilePath)
    {
        this.remoteScheduleUploadFilePath = remoteScheduleUploadFilePath;
        this.localUploadFilePath = localUploadFilePath;
    }

    public String getRemoteScheduleUploadFilePath()
    {
        return remoteScheduleUploadFilePath;
    }

    public void setRemoteScheduleUploadFilePath(String remoteScheduleUploadFilePath)
    {
        this.remoteScheduleUploadFilePath = remoteScheduleUploadFilePath;
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
        return "ScheduleUpload{" +
            "remoteScheduleUploadFilePath='" + remoteScheduleUploadFilePath + '\'' +
            ", localUploadFilePath='" + localUploadFilePath + '\'' +
            '}';
    }
}
