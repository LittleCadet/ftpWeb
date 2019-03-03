package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.DeleteMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.Delete;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpDelete;
import com.myproj.service.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 单点删除
 * @Author LettleCadet
 * @Date 2019/3/3
 */
@Service
public class DeleteServiceImpl implements DeleteService
{
    private static final Logger logger = LoggerFactory.getLogger(DeleteServiceImpl.class.getName());

    @Autowired
    private DeleteMapper deleteMapper;

    @Autowired
    private UserFtpMapper userFtpMapper;

    @Autowired
    private FtpDelete ftpDelete;

    private String serviceName = "delete";

    @Override
    public int insert(Delete record)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter into DeleteServiceImpl.insert();record:" + record);
        }
        UserFtp userFtp = null;

        if(ftpDelete.deleteFile())
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
            logger.debug("exit from DeleteServiceImpl.insert();");
        }
        return deleteMapper.insert(record);
    }

    @Override
    public int insertSelective(Delete record)
    {
        return deleteMapper.insertSelective(record);
    }
}
