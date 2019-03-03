package com.myproj.entity;


import java.io.IOException;

import org.apache.commons.net.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Demo {


    private static String src = "TestBase64";


    public static void main(String[] args) {
        Base64Demo.jdkBase64();
        Base64Demo.commonsCodecBase64 ();
        //Base64Demo.bouncyCastleBase64 ();
    }

    //使用JDK的base64实现，
    public static void jdkBase64 (){
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(Base64Demo.src.getBytes());
        System.out.println("encode:  " + encode);

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            String decode = new String ( decoder.decodeBuffer(encode));
            System.out.println("decode:  " + decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //使用apache的commonsCodec实现TestBase64
    public static void commonsCodecBase64 (){
        byte[] encodeBytes = Base64.encodeBase64(Base64Demo.src.getBytes());
        String encode = new String (encodeBytes);
        System.out.println("encode:  " + encode);

        byte[] decodeBytes = Base64.decodeBase64(encode);
        String decode = new String(decodeBytes);
        System.out.println("decode:  " + decode);

    }

  /*  //使用bouncyCastlede实现
    public static void bouncyCastleBase64 () {
        byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(Base64Demo.src.getBytes()) ;
        String encode = new String (encodeBytes);
        System.out.println("encode:  " + encode);

        byte[] decodeBytes = org.bouncycastle.util.encoders.Base64.decode(encode);
        String decode = new String(decodeBytes);
        System.out.println("decode:  " + decode);

    }*/

}

