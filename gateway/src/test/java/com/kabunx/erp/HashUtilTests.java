package com.kabunx.erp;

import com.kabunx.erp.util.HashUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class HashUtilTests {
    @Resource
    HashUtil hashUtil;

    @Test
    void testGenerate() throws NoSuchAlgorithmException {
        String expected = "7dcf407fa84a0e0519c7991154c4148de0244d7589020c0d9842db9efad82094";
        String actual = hashUtil.generate("a1b2c3d4");
        Assertions.assertEquals(expected, actual);
    }
}
