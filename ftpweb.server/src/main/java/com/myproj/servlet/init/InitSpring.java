package com.myproj.servlet.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring的初始化
 * LettleCadet
 * 2018/12/28
 **/
public class InitSpring
{
    private static ApplicationContext context;

    // 日志打印器
    private static final Logger logger = LoggerFactory.getLogger("ORD_DEBUG");

    /**
     * spring容器初始化
     */
    public static ApplicationContext init()
    {
        if(null == context)
        {
            context = new ClassPathXmlApplicationContext(new String[]{"spring/spring.xml"});
        }

        return context;
    }
}
