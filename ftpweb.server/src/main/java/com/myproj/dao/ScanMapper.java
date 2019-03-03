package com.myproj.dao;

import com.myproj.entity.Scan;

public interface ScanMapper
{
    int insert(Scan record);

    int insertSelective(Scan record);
}