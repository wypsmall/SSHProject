/*
 * Created on 2005-10-26
 */
package com.imti.framework.common;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    // 将 s 进行 BASE64 编码
    public static String base64Encoder(String s) {
        if (s == null)
            return null;
        return new String(Base64.decodeBase64(s.getBytes()));
    } 

    // 将 BASE64 编码的字符串 s 进行解码
    public static String base64Decoder(String s) {
        if (s == null)
            return null;
        try {
            byte[] b = Base64.decodeBase64(s.getBytes());
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
}
