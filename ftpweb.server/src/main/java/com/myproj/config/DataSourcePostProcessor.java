package com.myproj.config;

import com.alibaba.druid.util.DruidPasswordCallback;
import com.myproj.tools.Base64Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
