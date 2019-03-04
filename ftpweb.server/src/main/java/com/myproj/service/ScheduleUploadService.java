package com.myproj.service;

import com.myproj.entity.ScheduleUpload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface ScheduleUploadService
{
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(ScheduleUpload record);

    Integer insertSelective(ScheduleUpload record);

    ScheduleUpload selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(ScheduleUpload record);

    Integer updateByPrimaryKey(ScheduleUpload record);
}
