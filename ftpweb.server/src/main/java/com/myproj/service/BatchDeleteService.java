package com.myproj.service;

import com.myproj.entity.BatchDelete;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface BatchDeleteService
{
    int insert(BatchDelete record);

    int insertSelective(BatchDelete record);
}
