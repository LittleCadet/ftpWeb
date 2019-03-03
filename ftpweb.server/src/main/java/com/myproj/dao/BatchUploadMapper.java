package com.myproj.dao;

import com.myproj.entity.BatchUpload;

public interface BatchUploadMapper {
    int insert(BatchUpload record);

    int insertSelective(BatchUpload record);
}