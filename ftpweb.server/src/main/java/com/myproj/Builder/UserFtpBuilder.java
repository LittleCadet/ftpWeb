package com.myproj.Builder;

import com.myproj.entity.UserFtp;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public class UserFtpBuilder
{
    public static UserFtp build(String userId,String serviceName,Integer status)
    {
        UserFtp userFtp = new UserFtp();
        userFtp.setUserId(userId);
        userFtp.setService(serviceName);
        userFtp.setStatus(status);

        return userFtp;
    }
}
