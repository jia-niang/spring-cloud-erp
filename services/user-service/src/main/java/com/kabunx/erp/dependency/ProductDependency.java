package com.kabunx.erp.dependency;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "erp-product")
public interface ProductDependency {

}
