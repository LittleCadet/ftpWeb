package com.myproj.dao;

import com.myproj.entity.BatchDelete;

public interface BatchDeleteMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(BatchDelete record);

    Integer insertSelective(BatchDelete record);

    BatchDelete selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(BatchDelete record);

    Integer updateByPrimaryKey(BatchDelete record);
}