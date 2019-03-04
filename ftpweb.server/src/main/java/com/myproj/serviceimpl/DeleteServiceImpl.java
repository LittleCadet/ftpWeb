package com.myproj.serviceimpl;

import com.myproj.Builder.UserFtpBuilder;
import com.myproj.constants.FtpConstants;
import com.myproj.dao.DeleteMapper;
import com.myproj.dao.UserFtpMapper;
import com.myproj.entity.Delete;
import com.myproj.entity.UserFtp;
import com.myproj.ftp.FtpDelete;
import com.myproj.service.DeleteService;
import com.myproj.tools.Base64;
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
    public Integer insert(Delete record)
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

        //查出表userFtp当前主键的值，并入库
        record.setCodeId(userFtpMapper.selectMaxCodeId());
        record.setCreateTime(userFtp.getCreateTime());
        record.setPassword(Base64.encode(record.getPassword().getBytes()));

        if (logger.isDebugEnabled())
        {
            logger.debug("exit from DeleteServiceImpl.insert(); record :" + record);
        }
        return deleteMapper.insert(record);
    }

    @Override
    public Integer insertSelective(Delete record)
    {
        return deleteMapper.insertSelective(record);
    }

    @Override
    public Delete selectByPrimaryKey(Integer id)
    {
        return deleteMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(Delete record)
    {
        return deleteMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Integer updateByPrimaryKey(Delete record)
    {
        return deleteMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id)
    {
        return deleteMapper.deleteByPrimaryKey(id);
    }
}
