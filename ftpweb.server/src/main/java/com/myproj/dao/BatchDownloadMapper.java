package com.myproj.dao;

import com.myproj.entity.BatchDownload;

public interface BatchDownloadMapper
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(BatchDownload record);

    Integer insertSelective(BatchDownload record);

    BatchDownload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(BatchDownload record);

    Integer updateByPrimaryKey(BatchDownload record);
}