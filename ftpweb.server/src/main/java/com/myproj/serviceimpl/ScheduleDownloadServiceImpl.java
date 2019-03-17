package com.myproj.serviceimpl;

import com.myproj.Builder.FtpUtilBuilder;
import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.ScheduleDownloadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.ScheduleDownload;
import com.myproj.entity.UserFtp;
import com.myproj.service.ScheduleDownloadService;
import com.myproj.tools.Base64Util;
import com.myproj.tools.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时单点下载
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class ScheduleDownloadServiceImpl implements ScheduleDownloadService
{
    private static final Logger logger = LoggerFactory.getLogger(ScheduleDownloadServiceImpl.class.getName());

    @Autowired
    private ScheduleDownloadMapper scheduleDownloadMapper;

    @Autowired
    private UserFtpMapper userFtpMapper;

    private String serviceName = "scheduleDownload";

    @Override
    public Integer insert(ScheduleDownload record)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter into ScheduleDownloadServiceImpl.insert();record:" + record);
        }

        UserFtp userFtp = UserFtpBuilder.build(record.getUserId(), serviceName, FtpConstants.SUCCESSED);

        //构建FtpUtil
        FtpUtilBuilder.build(record.getHost(),record.getAccount(),Base64Util.encode(record.getPassword().getBytes()));

        if (logger.isDebugEnabled())
        {
            logger.debug("request of userFtpMapper is :" + userFtp);
        }

        userFtpMapper.insert(userFtp);

        //查出表userFtp当前主键的值，并入库
        record.setCodeId(userFtpMapper.selectMaxCodeId());
        record.setCreateTime(userFtp.getCreateTime());
        record.setPassword(Base64Util.encode(record.getPassword().getBytes()));
        record.setReTryTimes(FtpUtil.getReTryTimes());
        record.setTimeOut(FtpUtil.getTimeOut());

        if (logger.isDebugEnabled())
        {
            logger.debug("exit from ScheduleDownloadServiceImpl.insert(); codeId :" + record.getCodeId() + ",success to insert into userFtp,Upload");
        }

        return scheduleDownloadMapper.insert(record);
    }

    @Override
    public Integer insertSelective(ScheduleDownload record)
    {
        return scheduleDownloadMapper.insertSelective(record);
    }

    @Override
    public ScheduleDownload selectByPrimaryKey(Integer id)
    {
        return scheduleDownloadMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ScheduleDownload record)
    {
        return scheduleDownloadMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer updateByPrimaryKey(ScheduleDownload record)
    {
        return scheduleDownloadMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id)
    {
        return scheduleDownloadMapper.deleteByPrimaryKey(id);
    }
}
