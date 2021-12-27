package com.kabunx.erp.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EncryptUtils {
    /**
     * 算法
     */
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5PADDING";

    private static final String KEY_FILL = "qwertyuiopasdfghjklzxcvbnm";

    /**
     * 解密缓存
     */
    private static Map<String, Cipher> decryptMap = null;

    /**
     * 加密Cipher缓存
     */
    private static Map<String, Cipher> encryptMap = null;

    private static Map<String, Cipher> getEncryptMap() {
        if (encryptMap == null) {
            encryptMap = new ConcurrentHashMap<>();
        }
        return encryptMap;
    }

    private static Map<String, Cipher> getDecryptMap() {
        if (decryptMap == null) {
            decryptMap = new ConcurrentHashMap<>();
        }
        return decryptMap;
    }

    private static final String sha256 = "SHA-256";

    /**
     * 该方法兼容PHP加密,用于鉴权
     *
     * @param str 需加密的字符串
     * @return hash
     * @throws NoSuchAlgorithmException 异常
     */
    public static String encryptSha256(String str) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance(sha256);
        byte[] shaByteArr = hash.digest(str.getBytes());
        StringBuilder hexStrBuilder = new StringBuilder();
        for (byte b : shaByteArr) {
            hexStrBuilder.append(String.format("%02x", b));
        }
        return hexStrBuilder.toString();
    }

    /**
     * 加密字符串（可指定加密密钥）
     *
     * @param input 待加密文本
     * @param key   密钥（可选）
     * @return 加密后的数据
     */
    public static String encrypt(String input, String... key) {
        String seedKey = ObjectUtils.notEmpty(key) ? key[0] : getDefaultKey();
        try {
            Cipher cipher = getEncrypt(seedKey);
            byte[] enBytes = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(enBytes);
        } catch (Exception e) {
            log.error("加密出错:" + input, e);
            return input;
        }
    }

    /**
     * 解密字符串
     *
     * @param input 待解密文本
     * @param key   加密key（可选）
     * @return 解密后的数据
     */
    public static String decrypt(String input, String... key) {
        if (ObjectUtils.isEmpty(input)) {
            return input;
        }
        String seedKey = ObjectUtils.notEmpty(key) ? key[0] : getDefaultKey();
        try {
            Cipher cipher = getDecrypt(seedKey);
            byte[] deBytes = Base64.getDecoder().decode(input.getBytes());
            return new String(cipher.doFinal(deBytes));
        } catch (Exception e) {
            log.error("解密出错:" + input, e);
            return input;
        }
    }

    /**
     * 获取指定key的加密器
     *
     * @param key 加密密钥
     * @return 加密器
     * @throws Exception 异常信息
     */
    private static Cipher getEncrypt(String key) throws Exception {
        byte[] keyBytes = getKey(key);
        Cipher encryptor = getEncryptMap().get(new String(keyBytes));
        if (encryptor == null) {
            SecretKeySpec skype = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            encryptor = Cipher.getInstance(CIPHER_ALGORITHM);
            encryptor.init(Cipher.ENCRYPT_MODE, skype);
            // 放入缓存
            getEncryptMap().put(key, encryptor);
        }
        return encryptor;
    }

    /**
     * 获取指定key的解密器
     *
     * @param key 解密密钥
     * @return 解密器
     * @throws Exception 异常
     */
    private static Cipher getDecrypt(String key) throws Exception {
        byte[] keyBytes = getKey(key);
        Cipher decryptor = getDecryptMap().get(new String(keyBytes));
        if (decryptor == null) {
            SecretKeySpec skype = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            decryptor = Cipher.getInstance(CIPHER_ALGORITHM);
            decryptor.init(Cipher.DECRYPT_MODE, skype);
            // 放入缓存
            getDecryptMap().put(key, decryptor);
        }
        return decryptor;
    }

    /**
     * 获取key，如非16位则调整为16位（必须是16、32、64）
     *
     * @param seed 原始加密SEED
     * @return 加密KEY
     */
    private static byte[] getKey(String seed) {
        if (ObjectUtils.isEmpty(seed)) {
            seed = getDefaultKey();
        }
        if (seed.length() < 16) {
            seed = seed + StrUtils.cut(KEY_FILL, 16 - seed.length());
        } else if (seed.length() > 16) {
            seed = StrUtils.cut(seed, 16);
        }
        return seed.getBytes();
    }

    /**
     * TODO 默认加密seed（可通过配置文件）
     */
    private static String getDefaultKey() {
        String defaultKey = "xxx";
        return ObjectUtils.notEmpty(defaultKey) ? defaultKey : "ymtd";
    }
}
