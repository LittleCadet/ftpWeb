package com.myproj.service;

import com.myproj.entity.Upload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface UploadServcie
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Upload record);

    Integer insertSelective(Upload record);

    Upload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Upload record);

    Integer updateByPrimaryKey(Upload record);
}
