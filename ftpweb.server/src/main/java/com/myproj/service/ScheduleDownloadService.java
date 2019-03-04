package com.myproj.service;

import com.myproj.entity.ScheduleDownload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface ScheduleDownloadService
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(ScheduleDownload record);

    Integer insertSelective(ScheduleDownload record);

    ScheduleDownload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(ScheduleDownload record);

    Integer updateByPrimaryKey(ScheduleDownload record);
}
