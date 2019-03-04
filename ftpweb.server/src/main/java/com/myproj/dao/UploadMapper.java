package com.myproj.dao;

import com.myproj.entity.Upload;

public interface UploadMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Upload record);

    Integer insertSelective(Upload record);

    Upload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Upload record);

    Integer updateByPrimaryKey(Upload record);
}