package com.kabunx.erp;

import com.kabunx.erp.util.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AESUtilsTests {

    private final String decryptStr = "sms-code|1638261467321";
    private final String encryptStr = "+WiNK2WNJQqHsyZsacWfKsRUaBpVX7L2cVG7Rl88uqc=";
    // 必须16位、32位
    private final String key = "1234567890123456";

    @Test
    void testEncrypt() {
        String actualStr = AESUtils.encrypt(decryptStr, key);
        Assertions.assertEquals(encryptStr, actualStr);
    }

    @Test
    void testDecrypt() {
        String actualStr = AESUtils.decrypt(encryptStr, key);
        Assertions.assertEquals(decryptStr, actualStr);
    }
}
