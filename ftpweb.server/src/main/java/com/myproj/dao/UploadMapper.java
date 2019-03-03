package com.myproj.dao;

import com.myproj.entity.Upload;

public interface UploadMapper {
    int insert(Upload record);

    int insertSelective(Upload record);
}