package com.myproj.tools;

import org.junit.Test;

import static org.apache.commons.net.util.Base64.decodeBase64;
import static org.apache.commons.net.util.Base64.encodeBase64;;

/**
 * Base64加密
 * 使用apache的commonsCodec实现TestBase64,
 * 通过String.getBytes()可获得byte[],通过new String(byte[])的方式，可将byte[]转换为String
 * LettleCadet
 * 2019/3/4
 **/
public class Base64Util
{
    /**
     * Base64加密
     * @param binaryData
     * @return
     */
    public static String encode(byte[] binaryData)
    {
        return new String(encodeBase64(binaryData));
    }

    /**
     * base64解密
     * @param base64Data
     * @return
     */
    public static String decode(byte[] base64Data)
    {
        return new String(decodeBase64(base64Data));
    }
}
