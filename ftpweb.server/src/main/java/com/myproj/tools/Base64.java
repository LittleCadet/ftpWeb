package com.myproj.tools;

/**
 * Base64加密
 * 使用apache的commonsCodec实现TestBase64,
 * 通过String.getBytes()可获得byte[],通过new String(byte[])的方式，可将byte[]转换为String
 * 沈燮
 * 2019/3/4
 **/
public class Base64
{
    /**
     * Base64加密
     * @param binaryData
     * @return
     */
    public static String commonsCodecBase64(byte[] binaryData)
    {
        byte[] encodeBytes = org.apache.commons.net.util.Base64.encodeBase64(binaryData);
        String encode = new String(encodeBytes);
        System.out.println("encode:  " + encode);

        return encode;
    }

    /**
     * base64解密
     * @param base64Data
     * @return
     */
    public static String decode(byte[] base64Data)
    {
        byte[] decodeBytes = org.apache.commons.net.util.Base64.decodeBase64(base64Data);
        String decode = new String(decodeBytes);
        System.out.println("decode:  " + decode);

        return decode;
    }
}
