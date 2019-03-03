package com.myproj.dao;

import com.myproj.entity.UserFtp;

public interface UserFtpMapper {
    int deleteByPrimaryKey(Integer codeId);

    int insert(UserFtp record);

    int insertSelective(UserFtp record);

    UserFtp selectByPrimaryKey(Integer codeId);

    int updateByPrimaryKeySelective(UserFtp record);

    int updateByPrimaryKey(UserFtp record);
}