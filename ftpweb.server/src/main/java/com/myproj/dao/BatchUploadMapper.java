package com.myproj.dao;

import com.myproj.entity.BatchUpload;

public interface BatchUploadMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(BatchUpload record);

    Integer insertSelective(BatchUpload record);

    BatchUpload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(BatchUpload record);

    Integer updateByPrimaryKey(BatchUpload record);
}