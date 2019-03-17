package com.myproj.Builder;

import com.myproj.tools.FtpUtil;

/**
 * 构造FtpUtil
 * <p>
 * LittleCadet
 * 2019/3/11
 **/
public class FtpUtilBuilder
{
    /**
     * 构造FtpUtil
     *
     * @param host     ip
     * @param account  用户名
     * @param password 密码
     * @return
     */
    public static void build(String host, String account, String password)
    {
        FtpUtil.setHost(host);
        FtpUtil.setAccount(account);
        FtpUtil.setPassword(password);
    }
}
