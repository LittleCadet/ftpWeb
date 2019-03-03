package com.myproj.dao;

import com.myproj.entity.ScheduleUpload;

public interface ScheduleUploadMapper
{
    int insert(ScheduleUpload record);

    int insertSelective(ScheduleUpload record);
}