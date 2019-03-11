package com.myproj.dao;

import com.myproj.entity.ScheduleDownload;

public interface ScheduleDownloadMapper extends CommonMapper
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(ScheduleDownload record);

    Integer insertSelective(ScheduleDownload record);

    ScheduleDownload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(ScheduleDownload record);

    Integer updateByPrimaryKey(ScheduleDownload record);
}