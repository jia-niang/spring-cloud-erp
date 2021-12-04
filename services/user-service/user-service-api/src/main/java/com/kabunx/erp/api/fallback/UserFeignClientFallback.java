package com.kabunx.erp.api.fallback;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.dto.UserFromDTO;
import com.kabunx.erp.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFeignClientFallback implements UserFeignClient {
    @Override
    public JsonResponse<UserVO> show(Integer id) {
        return withFallbackError("ID检索");
    }

    @Override
    public JsonResponse<UserVO> phone(String phone) {
        return withFallbackError("电话检索");
    }

    @Override
    public JsonResponse<UserVO> account(String account) {
        return withFallbackError("账号检索");
    }

    @Override
    public JsonResponse<UserVO> create(UserFromDTO userFromDTO) {
        return withFallbackError("创建用户");
    }

    private JsonResponse<UserVO> withFallbackError(String name) {
        log.error("远程调用用户服务【{}】异常降级", name);
        return JsonResponse.withFallbackError();
    }
}
