package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.ScheduleUploadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.ScheduleUpload;
import com.myproj.entity.UserFtp;
import com.myproj.service.ScheduleUploadService;
import com.myproj.tools.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时单点上传
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class ScheduleUploadServiceImpl implements ScheduleUploadService
{
    private static final Logger logger = LoggerFactory.getLogger(ScheduleUploadServiceImpl.class.getName());

    @Autowired
    private ScheduleUploadMapper scheduleUploadMapper;

    @Autowired
    private UserFtpMapper userFtpMapper;

    private String serviceName = "scheduleUpload";

    @Override
    public Integer insert(ScheduleUpload record)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter into ScheduleUploadServiceImpl.insert();record:" + record);
        }

        UserFtp userFtp = UserFtpBuilder.build(record.getUserId(), serviceName, FtpConstants.SUCCESSED);

        if (logger.isDebugEnabled())
        {
            logger.debug("request of userFtpMapper is :" + userFtp);
        }

        userFtpMapper.insert(userFtp);

        //查出表userFtp当前主键的值，并入库
        record.setCodeId(userFtpMapper.selectMaxCodeId());
        record.setCreateTime(userFtp.getCreateTime());
        record.setPassword(Base64.encode(record.getPassword().getBytes()));

        if (logger.isDebugEnabled())
        {
            logger.debug("exit from ScheduleUploadServiceImpl.insert(); record :" + record);
        }

        return scheduleUploadMapper.insert(record);
    }

    @Override
    public Integer insertSelective(ScheduleUpload record)
    {
        return scheduleUploadMapper.insertSelective(record);
    }

    @Override
    public ScheduleUpload selectByPrimaryKey(Integer id)
    {
        return scheduleUploadMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ScheduleUpload record)
    {
        return scheduleUploadMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer updateByPrimaryKey(ScheduleUpload record)
    {
        return scheduleUploadMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id)
    {
        return scheduleUploadMapper.deleteByPrimaryKey(id);
    }
}
