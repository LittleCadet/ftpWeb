package com.myproj.dao;

import com.myproj.entity.BatchDelete;

public interface BatchDeleteMapper {
    int insert(BatchDelete record);

    int insertSelective(BatchDelete record);
}