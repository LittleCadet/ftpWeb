package com.myproj.serviceimpl;

import com.myproj.Builder.FtpUtilBuilder;
import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.UploadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.Upload;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpUpload;
import com.myproj.service.UploadServcie;
import com.myproj.tools.Base64Util;
import com.myproj.tools.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 单点上传
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class UploadServcieImpl implements UploadServcie
{
    private static final Logger logger = LoggerFactory.getLogger(UploadServcieImpl.class.getName());

    @Autowired
    private UploadMapper uploadMapper;

    @Autowired
    private UserFtpMapper userFtpMapper;

    @Autowired
    private FtpUpload ftpUpload;

    private String serviceName = "upload";

    @Override
    public Integer insert(Upload record)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter into UploadServcieImpl.insert();record:" + record);
        }

        UserFtp userFtp = null;

        //构建FtpUtil
        FtpUtilBuilder.build(record.getHost(),record.getAccount(),record.getPassword());

        if (ftpUpload.upload(record.getLocalUploadFilePath(),record.getRemoteUploadFilePath()))
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

        //调用userFtpMapper接口入库
        userFtpMapper.insert(userFtp);

        //查出表userFtp当前主键的值，并入库
        record.setCodeId(userFtpMapper.selectMaxCodeId());
        record.setCreateTime(userFtp.getCreateTime());
        record.setPassword(Base64Util.encode(record.getPassword().getBytes()));

        if (logger.isDebugEnabled())
        {
            logger.debug("exit from UploadServcieImpl.insert();record :" + record);
        }
        return uploadMapper.insert(record);
    }

    @Override
    public Integer insertSelective(Upload record)
    {
        return uploadMapper.insertSelective(record);
    }

    @Override
    public Upload selectByPrimaryKey(Integer id)
    {
        return uploadMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(Upload record)
    {
        return uploadMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer updateByPrimaryKey(Upload record)
    {
        return uploadMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id)
    {
        return uploadMapper.deleteByPrimaryKey(id);
    }
}
