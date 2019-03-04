package com.myproj.service;

import com.myproj.entity.BatchDelete;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface BatchDeleteService
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(BatchDelete record);

    Integer insertSelective(BatchDelete record);

    BatchDelete selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(BatchDelete record);

    Integer updateByPrimaryKey(BatchDelete record);
}
