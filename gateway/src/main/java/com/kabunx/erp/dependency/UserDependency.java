package com.kabunx.erp.dependency;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "erp-user")
public interface UserDependency {
}
