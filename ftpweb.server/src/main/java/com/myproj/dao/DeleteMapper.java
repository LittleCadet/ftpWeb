package com.myproj.dao;

import com.myproj.entity.Delete;

public interface DeleteMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Delete record);

    Integer insertSelective(Delete record);

    Delete selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Delete record);

    Integer updateByPrimaryKey(Delete record);
}