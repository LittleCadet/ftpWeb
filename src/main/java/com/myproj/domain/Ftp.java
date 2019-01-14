package com.myproj.domain;

import com.myproj.domain.init.InitSpring;
import com.myproj.ftp.FtpUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

/**
 * Ftp的实体类
 * 沈燮
 * 2019/1/3
 **/
public class Ftp
{
    private static final Logger logger = LoggerFactory.getLogger(Ftp.class.getName());

    public static void main(String[] args)
    {
        try
        {
            logger.debug("log4j: debug is ok now");

            logger.info("log4j: info is ok now");

            logger.warn("log4j: warn is ok now");

            logger.error("log4j:error is ok now");

            ApplicationContext context = InitSpring.init();

            if(logger.isDebugEnabled())
            {
                logger.debug("--------------time:"+ new Date() +"--------------");
            }

            //第5秒有下载任务【download】，刚开始运行时有下载任务[scanDirectory]
            Thread.sleep(20000);
        }
        catch (InterruptedException e)
        {
            logger.error("Thead exception");
        }

    }
}
