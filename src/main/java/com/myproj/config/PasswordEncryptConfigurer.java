package com.myproj.config;

import java.util.Properties;
import com.myproj.tools.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 解码
 * 沈燮
 * 2019/3/8
 **/
public class PasswordEncryptConfigurer extends PropertyPlaceholderConfigurer
{
    private static final Logger logger = LoggerFactory.getLogger(PasswordEncryptConfigurer.class.getName());

    /**
     * 对properties中密码解密，重新放入xml的properties中
     *
     * @param beanFactory
     * @param props
     * @throws BeansException
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
        throws BeansException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("enter into PasswordEncryptConfigurer.processProperties(),system is decoding files");
        }
        try
        {
            String JdPassword = props.getProperty("jdbc.password");
            if (JdPassword != null && JdPassword.startsWith("{DES}"))
            {

                JdPassword = JdPassword.substring("{DES}".length());

                //解密  password
                JdPassword = Base64Util.decode(JdPassword.getBytes());
            }

            //将解密后的密码放入Properties中
            props.setProperty("jdbc.password", JdPassword);
            super.processProperties(beanFactory, props);
        }
        catch (Exception e)
        {
            logger.error("something wrong with processing decoding in PasswordEncryptConfigurer.processProperties()");
            throw new BeanInitializationException(e.getMessage());
        }
    }
}
