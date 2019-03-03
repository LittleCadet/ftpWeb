package com.myproj.service;

import com.myproj.entity.ScheduleUpload;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public interface ScheduleUploadService
{
    int insert(ScheduleUpload record);

    int insertSelective(ScheduleUpload record);
}
