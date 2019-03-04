package com.myproj.service;

import com.myproj.entity.BatchUpload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface BatchUploadService
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(BatchUpload record);

    Integer insertSelective(BatchUpload record);

    BatchUpload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(BatchUpload record);

    Integer updateByPrimaryKey(BatchUpload record);
}
