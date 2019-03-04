package com.myproj.entity;

import java.sql.Timestamp;

public class UserFtp
{
    private Integer codeId;

    private String userId;

    private String service;

    private Integer status;

    private Timestamp createTime;

    public UserFtp()
    {
    }

    public UserFtp(Integer codeId, String userId, String service)
    {
        this.codeId = codeId;
        this.userId = userId;
        this.service = service;
    }

    public Integer getCodeId()
    {
        return codeId;
    }

    public void setCodeId(Integer codeId)
    {
        this.codeId = codeId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service == null ? null : service.trim();
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString()
    {
        return "UserFtp{" +
            "codeId=" + codeId +
            ", userId='" + userId + '\'' +
            ", service='" + service + '\'' +
            ", status=" + status +
            ", createTime=" + createTime +
            '}';
    }
}