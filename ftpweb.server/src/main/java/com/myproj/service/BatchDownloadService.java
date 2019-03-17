package com.myproj.service;

import com.myproj.entity.BatchDownload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface BatchDownloadService
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(BatchDownload record);

    Integer insertSelective(BatchDownload record);

    BatchDownload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(BatchDownload record);

    Integer updateByPrimaryKey(BatchDownload record);
}
