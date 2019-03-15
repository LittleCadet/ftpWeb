package base;

import init.InitSpring;
import org.springframework.context.ApplicationContext;

/**
 * @Author LettleCadet
 * @Date 2019/3/3
 */
public class Base
{
    public static ApplicationContext context = InitSpring.init();
}
