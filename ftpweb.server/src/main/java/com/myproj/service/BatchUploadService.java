package com.myproj.service;

import com.myproj.entity.BatchUpload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface BatchUploadService
{
    int insert(BatchUpload record);

    int insertSelective(BatchUpload record);
}
