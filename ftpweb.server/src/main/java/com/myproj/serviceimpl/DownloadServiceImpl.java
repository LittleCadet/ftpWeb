package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.DownloadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.Download;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpDownload;
import com.myproj.service.DownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 单点下载
 *
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class DownloadServiceImpl implements DownloadService
{
    private static final Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class.getName());

    @Autowired
    private DownloadMapper downloadMapper;

    @Autowired
    private UserFtpMapper userFtpMapper;

    @Autowired
    private FtpDownload ftpDownload;

    private String serviceName = "download";

    @Override
    public int insert(Download record)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter into DownloadServiceImpl.insert();record:" + record);
        }
        UserFtp userFtp = null;

        if (ftpDownload.download())
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
            logger.debug("exit from DownloadServiceImpl.insert();");
        }
        return downloadMapper.insert(record);
    }

    @Override
    public int insertSelective(Download record)
    {
        return downloadMapper.insertSelective(record);
    }
}
