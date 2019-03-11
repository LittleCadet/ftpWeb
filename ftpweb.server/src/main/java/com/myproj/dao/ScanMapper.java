package com.myproj.dao;

import com.myproj.entity.Scan;

public interface ScanMapper
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Scan record);

    Integer insertSelective(Scan record);

    Scan selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Scan record);

    Integer updateByPrimaryKey(Scan record);
}