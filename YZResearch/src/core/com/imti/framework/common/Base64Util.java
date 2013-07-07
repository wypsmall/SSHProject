/*
 * Created on 2005-10-26
 */
package com.imti.framework.common;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    // �� s ���� BASE64 ����
    public static String base64Encoder(String s) {
        if (s == null)
            return null;
        return new String(Base64.decodeBase64(s.getBytes()));
    } 

    // �� BASE64 ������ַ��� s ���н���
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
