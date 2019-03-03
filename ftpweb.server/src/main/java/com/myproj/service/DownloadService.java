package com.myproj.service;

import com.myproj.entity.Download;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface DownloadService
{
    int insert(Download record);

    int insertSelective(Download record);
}
