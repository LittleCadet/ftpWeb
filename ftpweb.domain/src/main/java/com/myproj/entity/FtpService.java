package com.myproj.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @Author LettleCadet
 * @Timestamp 2019/2/26
 */
public class FtpService
{
    //服务器ip
    @NotBlank
    @Size(min = 7, max = 15)//作用于String,不能为null，且trim（）后，长度>0
    private String host;

    //用户名
    @NotEmpty //不能为null，且长度>0
    private String account;

    //密码
    @NotEmpty
    private String password;

    //重试次数
    private String reTryTimes;

    //ftp会话超时时间
    private String timeOut;

    private String userId;

    private Timestamp createTime;

    private Integer codeId;

    private Integer id;

    public FtpService()
    {
    }

    public FtpService(String host, String account, String password, String userId)
    {
        this.host = host;
        this.account = account;
        this.password = password;
        this.userId = userId;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getReTryTimes()
    {
        return reTryTimes;
    }

    public void setReTryTimes(String reTryTimes)
    {
        this.reTryTimes = reTryTimes;
    }

    public String getTimeOut()
    {
        return timeOut;
    }

    public void setTimeOut(String timeOut)
    {
        this.timeOut = timeOut;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public Integer getCodeId()
    {
        return codeId;
    }

    public void setCodeId(Integer codeId)
    {
        this.codeId = codeId;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "FtpService{" +
            "host='" + host + '\'' +
            ", account='" + account + '\'' +
            ", password='" + password + '\'' +
            ", reTryTimes='" + reTryTimes + '\'' +
            ", timeOut='" + timeOut + '\'' +
            ", userId='" + userId + '\'' +
            ", createTime=" + createTime +
            ", codeId=" + codeId +
            ", id=" + id +
            '}';
    }
}
