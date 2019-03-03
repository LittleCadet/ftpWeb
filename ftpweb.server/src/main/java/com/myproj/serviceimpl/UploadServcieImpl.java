package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.UploadMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.Upload;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpUpload;
import com.myproj.service.UploadServcie;
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

    //@Autowired
    private FtpUpload ftpUpload;

    private String serviceName = "upload";

    @Override
    public int insert(Upload record)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter into UploadServcieImpl.insert();record:" + record);
        }

        UserFtp userFtp = null;

        if (ftpUpload.upload())
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
            logger.debug("exit from UploadServcieImpl.insert();");
        }
        return uploadMapper.insert(record);
    }

    @Override
    public int insertSelective(Upload record)
    {
        return uploadMapper.insertSelective(record);
    }

    public void setFtpUpload(FtpUpload ftpUpload)
    {
        this.ftpUpload = ftpUpload;
    }
}
