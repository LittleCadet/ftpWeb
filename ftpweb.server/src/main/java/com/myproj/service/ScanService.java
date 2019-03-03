package com.myproj.service;

import com.myproj.entity.Scan;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface ScanService
{
    int insert(Scan record);

    int insertSelective(Scan record);
}
