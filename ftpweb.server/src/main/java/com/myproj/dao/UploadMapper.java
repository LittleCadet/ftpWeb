package com.myproj.dao;

import com.myproj.entity.Upload;

public interface UploadMapper extends CommonMapper
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Upload record);

    Integer insertSelective(Upload record);

    Upload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Upload record);

    Integer updateByPrimaryKey(Upload record);
}