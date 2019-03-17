package com.myproj.service;

import com.myproj.entity.Download;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface DownloadService
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Download record);

    Integer insertSelective(Download record);

    Download selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Download record);

    Integer updateByPrimaryKey(Download record);
}
