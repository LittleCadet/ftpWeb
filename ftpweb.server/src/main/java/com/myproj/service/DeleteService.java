package com.myproj.service;

import com.myproj.entity.Delete;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface DeleteService
{
    int insert(Delete record);

    int insertSelective(Delete record);
}
