package com.kabunx.erp;

import com.kabunx.erp.util.HashUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@SpringBootTest
public class HashUtilTests {

    @Test
    void testHashEncrypt() throws NoSuchAlgorithmException {
        String expected = "7dcf407fa84a0e0519c7991154c4148de0244d7589020c0d9842db9efad82094";
        String actual = HashUtils.encryptSha256("a1b2c3d4");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testOptionalEmpty() {
        Optional<String> empty = Optional.empty();
        Assertions.assertFalse(empty.isPresent());
    }
}
