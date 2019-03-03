package com.myproj.service;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface BatchDownloadService
{
    int insert(com.myproj.entity.BatchDownload record);

    int insertSelective(com.myproj.entity.BatchDownload record);
}
