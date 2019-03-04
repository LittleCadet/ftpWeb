package com.myproj.service;

import com.myproj.entity.Delete;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface DeleteService
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Delete record);

    Integer insertSelective(Delete record);

    Delete selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Delete record);

    Integer updateByPrimaryKey(Delete record);
}
