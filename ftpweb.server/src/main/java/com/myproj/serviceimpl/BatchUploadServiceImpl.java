package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.BatchUploadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.BatchUpload;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpBatchUpload;
import com.myproj.service.BatchUploadService;
import com.myproj.tools.Base64;
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
    public Integer insert(BatchUpload record)
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

        //查出表userFtp当前主键的值，并入库
        record.setCodeId(userFtpMapper.selectMaxCodeId());
        record.setCreateTime(userFtp.getCreateTime());
        record.setPassword(Base64.encode(record.getPassword().getBytes()));

        if (logger.isDebugEnabled())
        {
            logger.debug("exit from BatchUploadServiceImpl.insert();record :" + record);
        }

        return batchUploadMapper.insert(record);
    }

    @Override
    public Integer insertSelective(BatchUpload record)
    {
        return batchUploadMapper.insertSelective(record);
    }

    @Override
    public BatchUpload selectByPrimaryKey(Integer id)
    {
        return batchUploadMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(BatchUpload record)
    {
        return batchUploadMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer updateByPrimaryKey(BatchUpload record)
    {
        return batchUploadMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id)
    {
        return batchUploadMapper.deleteByPrimaryKey(id);
    }
}
