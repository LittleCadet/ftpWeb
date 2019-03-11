package com.myproj.dao;

import com.myproj.entity.ScheduleUpload;

public interface ScheduleUploadMapper
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(ScheduleUpload record);

    Integer insertSelective(ScheduleUpload record);

    ScheduleUpload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(ScheduleUpload record);

    Integer updateByPrimaryKey(ScheduleUpload record);
}