package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author LettleCadet
 * @Date 2019/2/26
 */
public class Delete extends FtpService
{
    //ftp远程删除路径
    @NotBlank
    private String remoteDeleteFilePath;

    public Delete()
    {
    }

    public Delete(@NotBlank String remoteDeleteFilePath)
    {
        this.remoteDeleteFilePath = remoteDeleteFilePath;
    }

    public String getRemoteDeleteFilePath()
    {
        return remoteDeleteFilePath;
    }

    public void setRemoteDeleteFilePath(String remoteDeleteFilePath)
    {
        this.remoteDeleteFilePath = remoteDeleteFilePath;
    }

    @Override
    public String toString()
    {
        return super.toString() + ",Delete{" +
            "remoteDeleteFilePath='" + remoteDeleteFilePath + '\'' +
            '}';
    }
}
