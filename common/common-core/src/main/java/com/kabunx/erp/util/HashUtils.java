package com.kabunx.erp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private final static String sha256 = "SHA-256";

    public static String encryptSha256(String str) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance(sha256);
        byte[] shaByteArr = hash.digest(str.getBytes());
        StringBuilder hexStrBuilder = new StringBuilder();
        for (byte b : shaByteArr) {
            hexStrBuilder.append(String.format("%02x", b));
        }
        return hexStrBuilder.toString();
    }
}
