package com.myproj.service;

import com.myproj.entity.Upload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface UploadServcie
{
    int insert(Upload record);

    int insertSelective(Upload record);
}
