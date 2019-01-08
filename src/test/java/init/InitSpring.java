package init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring的初始化
 * 沈燮
 * 2018/12/28
 **/
public class InitSpring
{
    private static ApplicationContext context;

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
