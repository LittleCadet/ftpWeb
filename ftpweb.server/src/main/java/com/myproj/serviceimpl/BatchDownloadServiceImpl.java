package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.BatchDownloadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.BatchDownload;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpBatchDownload;
import com.myproj.service.BatchDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 批量下载
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class BatchDownloadServiceImpl implements BatchDownloadService
{
    private static final Logger logger = LoggerFactory.getLogger(BatchDownloadServiceImpl.class.getName());

    @Autowired
    private BatchDownloadMapper batchDownloadMapper;

    @Autowired
    private UserFtpMapper userFtpMapper;

    @Autowired
    private FtpBatchDownload ftpBatchDownload;

    private String serviceName = "batchDownload";

    @Override
    public int insert(BatchDownload record)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("enter into BatchDownloadServiceImpl.insert();record:" + record);
        }
        UserFtp userFtp = null;

        if(ftpBatchDownload.batchDownload())
        {
            userFtp = UserFtpBuilder.build(record.getUserId(),serviceName, FtpConstants.SUCCESSED);
        }
        else
        {
            userFtp = UserFtpBuilder.build(record.getUserId(),serviceName, FtpConstants.FAILED);
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("request of userFtpMapper is :" + userFtp);
        }

        userFtpMapper.insert(userFtp);

        if(logger.isDebugEnabled())
        {
            logger.debug("exit from BatchDownloadServiceImpl.insert();");
        }

        return batchDownloadMapper.insert(record);
    }

    @Override
    public int insertSelective(BatchDownload record)
    {
        return batchDownloadMapper.insertSelective(record);
    }
}
