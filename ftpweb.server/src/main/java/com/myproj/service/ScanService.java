package com.myproj.service;

import com.myproj.entity.Scan;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface ScanService
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Scan record);

    Integer insertSelective(Scan record);

    Scan selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Scan record);

    Integer updateByPrimaryKey(Scan record);
}
