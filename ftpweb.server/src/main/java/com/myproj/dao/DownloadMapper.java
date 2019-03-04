package com.myproj.dao;

import com.myproj.entity.Download;

public interface DownloadMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Download record);

    Integer insertSelective(Download record);

    Download selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Download record);

    Integer updateByPrimaryKey(Download record);
}