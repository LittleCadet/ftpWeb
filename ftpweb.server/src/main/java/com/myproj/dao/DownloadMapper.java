package com.myproj.dao;

import com.myproj.entity.Download;

public interface DownloadMapper
{
    int insert(Download record);

    int insertSelective(Download record);
}