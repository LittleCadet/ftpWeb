package com.myproj.dao;

import com.myproj.entity.UserFtp;

public interface UserFtpMapper {
    Integer deleteByPrimaryKey(Integer codeId);

    Integer insert(UserFtp record);

    Integer insertSelective(UserFtp record);

    UserFtp selectByPrimaryKey(Integer codeId);

    Integer updateByPrimaryKeySelective(UserFtp record);

    Integer updateByPrimaryKey(UserFtp record);

    Integer selectMaxCodeId();
}