package com.kabunx.erp.api.fallback;

import com.kabunx.erp.api.MemberFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.vo.MemberVO;
import org.springframework.stereotype.Component;

@Component
public class MemberFeignClientFallback implements MemberFeignClient {
    @Override
    public JsonResponse<MemberVO> findByUserId(Integer id) {
        return JsonResponse.withFallbackError();
    }
}
