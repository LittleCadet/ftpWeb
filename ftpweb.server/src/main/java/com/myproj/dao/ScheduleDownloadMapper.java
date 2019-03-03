package com.myproj.dao;

import com.myproj.entity.ScheduleDownload;

public interface ScheduleDownloadMapper
{
    int insert(ScheduleDownload record);

    int insertSelective(ScheduleDownload record);
}