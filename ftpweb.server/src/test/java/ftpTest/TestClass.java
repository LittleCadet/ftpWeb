package ftpTest;

import com.myproj.servlet.init.InitSpring;

/**
 * 沈燮
 * 2019/3/8
 **/
public class TestClass extends InitSpring
{
    private String password;

    @org.junit.Test
    public void showInfo()
    {
        TestClass testClass = (TestClass)InitSpring.init().getBean("testClass");
        System.out.println("解密结果："+testClass.getPassword());
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
