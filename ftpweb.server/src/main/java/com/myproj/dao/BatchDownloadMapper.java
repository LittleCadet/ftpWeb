package com.myproj.dao;

import com.myproj.entity.BatchDownload;

public interface BatchDownloadMapper {
    int insert(BatchDownload record);

    int insertSelective(BatchDownload record);
}