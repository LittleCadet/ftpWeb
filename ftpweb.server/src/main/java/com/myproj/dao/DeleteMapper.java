package com.myproj.dao;

import com.myproj.entity.Delete;

public interface DeleteMapper
{
    int insert(Delete record);

    int insertSelective(Delete record);
}