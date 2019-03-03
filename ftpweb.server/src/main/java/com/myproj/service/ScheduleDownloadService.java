package com.myproj.service;

import com.myproj.entity.ScheduleDownload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface ScheduleDownloadService
{
    int insert(ScheduleDownload record);

    int insertSelective(ScheduleDownload record);
}
