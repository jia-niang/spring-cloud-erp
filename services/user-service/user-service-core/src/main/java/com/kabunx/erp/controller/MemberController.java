package com.kabunx.erp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kabunx.erp.api.MemberFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.service.MemberService;
import com.kabunx.erp.vo.MemberVo;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MemberController implements MemberFeignClient {

    @Resource
    MemberService memberService;

    @Override
    public JsonResponse<MemberVo> findById(Integer id) {
        return JsonResponse.success(memberService.findById(id));
    }
}
