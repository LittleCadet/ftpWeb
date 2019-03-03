package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.BatchDeleteMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.BatchDelete;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpBatchDelete;
import com.myproj.service.BatchDeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 批量删除
 *
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class BatchDeleteServiceImpl implements BatchDeleteService
{
    private static final Logger logger = LoggerFactory.getLogger(BatchDeleteServiceImpl.class.getName());

    @Autowired
    private BatchDeleteMapper batchDeleteMapper;

    @Autowired
    private UserFtpMapper userFtpMapper;

    @Autowired
    private FtpBatchDelete ftpBatchDelete;

    private String serviceName = "batchDelete";

    @Override
    public int insert(BatchDelete record)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("enter into BatchDeleteServiceImpl.insert();record:" + record);
        }

        UserFtp userFtp = null;
        //执行批量下载动作
        if (ftpBatchDelete.batchDeleteFile())
        {
            //构造UserFtp
            userFtp = UserFtpBuilder.build(record.getUserId(),serviceName, FtpConstants.SUCCESSED);

        }
        else
        {
            //构造UserFtp
            userFtp = UserFtpBuilder.build(record.getUserId(), serviceName, FtpConstants.FAILED);
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("request of userFtpMapper is :" + userFtp);
        }

        userFtpMapper.insert(userFtp);

        if(logger.isDebugEnabled())
        {
            logger.debug("exit from BatchDeleteServiceImpl.insert()");
        }
        return batchDeleteMapper.insert(record);
    }

    @Override
    public int insertSelective(BatchDelete record)
    {
        return batchDeleteMapper.insert(record);
    }
}
