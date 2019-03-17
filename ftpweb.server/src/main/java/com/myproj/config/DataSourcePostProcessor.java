package com.myproj.config;

import com.alibaba.druid.util.DruidPasswordCallback;
import com.myproj.tools.Base64Util;
import lombok.Setter;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * 解密数据库密码处理类
 * LettleCadet
 * 2019/3/17
 */
public class DataSourcePostProcessor extends DruidPasswordCallback
{

    /**
     * 序列号
     */
    private static final long serialVersionUID = 3886590546275120091L;

    /**
     * 日志类
     */
    private final static Logger logger = LoggerFactory.getLogger(DataSourcePostProcessor.class);

    @Setter
    private Resource config;

    public void setProperties(Properties properties)
    {

        super.setProperties(properties);
        String JdPassword = properties.getProperty("password");
        if (StringUtils.isNotBlank(JdPassword))
        {
            try
            {
                if (JdPassword != null && JdPassword.startsWith("{DES}"))
                {

                    JdPassword = JdPassword.substring("{DES}".length());

                    //解密  password
                    JdPassword = Base64Util.decode(JdPassword.getBytes());
                }

                //将解密后的密码放入Properties中
                setPassword(JdPassword.toCharArray());
            }
            catch (Exception e)
            {
                logger.error("decrypt password error", e);
                setPassword(JdPassword.toCharArray());
            }
        }
    }
}
