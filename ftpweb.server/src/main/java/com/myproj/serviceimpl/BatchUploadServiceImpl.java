package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.BatchUploadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.BatchUpload;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpBatchUpload;
import com.myproj.service.BatchUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 批量上传
 *
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class BatchUploadServiceImpl implements BatchUploadService
{
    private static final Logger logger = LoggerFactory.getLogger(BatchUploadServiceImpl.class.getName());

    @Autowired
    private BatchUploadMapper batchUploadMapper;

    @Autowired
    private UserFtpMapper userFtpMapper;

    @Autowired
    private FtpBatchUpload ftpBatchUpload;

    private String serviceName = "batchUpload";

    @Override
    public int insert(BatchUpload record)
    {
        if (logger.isDebugEnabled())
    {
        logger.debug("enter into BatchUploadServiceImpl.insert();record:" + record);
    }
        UserFtp userFtp = null;

        if (ftpBatchUpload.batchUpload())
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
            logger.debug("exit from BatchUploadServiceImpl.insert();");
        }

        return batchUploadMapper.insert(record);
    }

    @Override
    public int insertSelective(BatchUpload record)
    {
        return batchUploadMapper.insertSelective(record);
    }
}
