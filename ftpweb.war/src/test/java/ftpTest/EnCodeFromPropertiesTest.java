package ftpTest;

import com.myproj.servlet.init.InitSpring;
import org.junit.Assert;
import org.junit.Test;

/**
 * 沈燮
 * 2019/3/8
 **/
public class EnCodeFromPropertiesTest extends InitSpring
{
    private String password;

    private String simple = "{DES}";

    @Test
    public void showInfo()
    {
        EnCodeFromPropertiesTest enCodeFromPropertiesTest = (EnCodeFromPropertiesTest)InitSpring.init().getBean("EnCodeFromPropertiesTest");
        Assert.assertTrue(enCodeFromPropertiesTest.getPassword().contains(simple));
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
}
