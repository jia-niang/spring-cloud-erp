package com.kabunx.erp.util;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashUtil {

    public String generate(String str) throws NoSuchAlgorithmException {
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        byte[] digest = hash.digest(str.getBytes());
        return new BASE64Encoder().encode(digest);
    }
}
