package ftpTest;

import org.junit.Test;

import static com.myproj.tools.Base64Util.decode;
import static com.myproj.tools.Base64Util.encode;

/**
 * @Author LettleCadet
 * @Date 2019/3/17
 */
public class Base64Test
{
    private String encode = "U3g5MjA3MDI=";

    private String un_encode = "Sx920702";

    @Test
    public void decodePassword()
    {
        System.out.println(decode(encode.getBytes()));
    }

    @Test
    public void encodePassword()
    {
        System.out.println(encode(un_encode.getBytes()));
    }
}
