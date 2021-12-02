package com.kabunx.erp.controller;

import com.kabunx.erp.api.AdminFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.service.AdminService;
import com.kabunx.erp.vo.AdminVO;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminController implements AdminFeignClient {
    @Resource
    AdminService adminService;

    @Override
    public JsonResponse<AdminVO> findById(Long id) {
        return JsonResponse.success(adminService.findByUserId(id));
    }
}
