package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author LettleCadet
 * @Date 2019/2/26
 */
public class BatchDelete extends FtpService
{
    //ftp批量删除路径
    @NotBlank
    private String batchDeleteFilePath;

    public BatchDelete()
    {
    }

    public BatchDelete(@NotBlank String batchDeleteFilePath)
    {
        this.batchDeleteFilePath = batchDeleteFilePath;
    }

    public String getBatchDeleteFilePath()
    {
        return batchDeleteFilePath;
    }

    public void setBatchDeleteFilePath(String batchDeleteFilePath)
    {
        this.batchDeleteFilePath = batchDeleteFilePath;
    }

    @Override
    public String toString()
    {
        return super.toString() + ",BatchDelete{" +
            "batchDeleteFilePath='" + batchDeleteFilePath + '\'' +
            '}';
    }
}
