package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.ScanMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.Scan;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.ScanDirectory;
import com.myproj.service.ScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 单点扫描
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class ScanServiceImpl implements ScanService
{
    private static final Logger logger = LoggerFactory.getLogger(ScanServiceImpl.class.getName());

    @Autowired
    private UserFtpMapper userFtpMapper;

    @Autowired
    private ScanMapper scanMapper;

    @Autowired
    private ScanDirectory scanDirectory;

    private String serviceName = "scan";

    @Override
    public int insert(Scan record)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter into ScanServiceImpl.insert();record:" + record);
        }
        UserFtp userFtp = null;

        if (scanDirectory.scan())
        {
            userFtp = UserFtpBuilder.build(record.getUserId(), serviceName, FtpConstants.SUCCESSED);
        }
        else
        {
            userFtp = UserFtpBuilder.build(record.getUserId(), serviceName, FtpConstants.FAILED);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("request of userFtpMapper is :" + userFtp);
        }

        userFtpMapper.insert(userFtp);

        if (logger.isDebugEnabled())
        {
            logger.debug("exit from ScanServiceImpl.insert();");
        }
        return scanMapper.insert(record);
    }

    @Override
    public int insertSelective(Scan record)
    {
        return scanMapper.insertSelective(record);
    }
}
