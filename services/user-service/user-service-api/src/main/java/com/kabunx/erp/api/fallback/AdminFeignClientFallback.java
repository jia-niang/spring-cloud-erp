package com.kabunx.erp.api.fallback;

import com.kabunx.erp.api.AdminFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.vo.AdminVO;
import org.springframework.stereotype.Component;

@Component
public class AdminFeignClientFallback implements AdminFeignClient {
    @Override
    public JsonResponse<AdminVO> findById(Integer id) {
        return JsonResponse.withFallbackError();
    }
}
