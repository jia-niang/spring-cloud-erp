package com.kabunx.erp.api.fallback;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.dto.UserDto;
import com.kabunx.erp.vo.MemberVo;
import com.kabunx.erp.vo.UserTokenVo;
import com.kabunx.erp.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFeignClientFallback implements UserFeignClient {
    @Override
    public JsonResponse<UserVo> list(String username) {
        errorLog();
        return JsonResponse.withFallbackError();
    }

    @Override
    public JsonResponse<UserVo> show(Integer id) {
        errorLog();
        return JsonResponse.withFallbackError();
    }

    @Override
    public JsonResponse<MemberVo> findByIdWithToken(Integer id) {
        errorLog();
        return JsonResponse.withFallbackError();
    }

    @Override
    public JsonResponse<UserVo> create(UserDto userDTO) {
        errorLog();
        return JsonResponse.withFallbackError();
    }

    private void errorLog() {
        log.error("Openfeign远程调用用户服务（创建用户）异常的降级方法");
    }

    private void errorLog(String message) {
        log.error(message);
    }
}
