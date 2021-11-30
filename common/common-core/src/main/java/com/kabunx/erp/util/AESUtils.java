package com.kabunx.erp.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * 简单的AES加密解密
 */
public class AESUtils {

    private static final String ALGORITHM = "AES";

    // "算法/模式/补码方式"NoPadding PkcsPadding
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String value, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(key));
            byte[] dataBytes = value.getBytes(StandardCharsets.UTF_8);
            byte[] originals = cipher.doFinal(dataBytes);
            return new BASE64Encoder().encode(originals);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String value, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(key));
            byte[] dataBytes = new BASE64Decoder().decodeBuffer(value);
            byte[] originals = cipher.doFinal(dataBytes);
            return new String(originals);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static SecretKeySpec generateKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
