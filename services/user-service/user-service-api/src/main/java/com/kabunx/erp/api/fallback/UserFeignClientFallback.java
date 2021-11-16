package com.kabunx.erp.api.fallback;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.dto.UserDTO;
import com.kabunx.erp.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFeignClientFallback implements UserFeignClient {
    @Override
    public JsonResponseBody<UserVO> findByAccount(String username) {
        log.error("Openfeign远程调用用户服务（查找用户）异常的降级方法");
        return JsonResponseBody.withFallbackError();
    }

    @Override
    public JsonResponseBody<UserVO> create(UserDTO userDTO) {
        log.error("Openfeign远程调用用户服务（创建用户）异常的降级方法");
        return JsonResponseBody.withFallbackError();
    }
}
