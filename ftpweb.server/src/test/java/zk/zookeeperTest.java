package zk;

import base.Base;
import com.myproj.service.RegisterService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author LettleCadet
 * @Date 2019/3/7
 */
public class zookeeperTest extends Base
{
    @Test
    public void registerService()
    {
        RegisterService registerService = (RegisterService)context.getBean("registerService");
        Assert.assertTrue(registerService.registerService());
    }
}
