package com.kabunx.erp.dependency;

import com.kabunx.erp.domain.AuthRequest;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.domain.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "erp-user")
public interface UserDependency {
    JsonResponseBody<UserVo> register(@RequestBody AuthRequest authRequest);
}
