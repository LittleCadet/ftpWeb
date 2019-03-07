package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.BatchDownloadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.BatchDownload;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpBatchDownload;
import com.myproj.service.BatchDownloadService;
import com.myproj.tools.Base64Util;
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
    public Integer insert(BatchDownload record)
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

        //查出表userFtp当前主键的值，并入库
        record.setCodeId(userFtpMapper.selectMaxCodeId());
        record.setCreateTime(userFtp.getCreateTime());
        record.setPassword(Base64Util.encode(record.getPassword().getBytes()));

        if(logger.isDebugEnabled())
        {
            logger.debug("exit from BatchDownloadServiceImpl.insert();record :" + record);
        }

        return batchDownloadMapper.insert(record);
    }

    @Override
    public Integer insertSelective(BatchDownload record)
    {
        return batchDownloadMapper.insertSelective(record);
    }

    @Override
    public BatchDownload selectByPrimaryKey(Integer id)
    {
        return batchDownloadMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(BatchDownload record)
    {
        return batchDownloadMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer updateByPrimaryKey(BatchDownload record)
    {
        return batchDownloadMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id)
    {
        return batchDownloadMapper.deleteByPrimaryKey(id);
    }
}
