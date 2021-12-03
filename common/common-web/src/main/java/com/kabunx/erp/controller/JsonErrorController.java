package com.kabunx.erp.controller;

import com.kabunx.erp.domain.JsonResponse;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class JsonErrorController extends AbstractErrorController {

    public JsonErrorController(
            ErrorAttributes errorAttributes,
            List<ErrorViewResolver> errorViewResolvers
    ) {
        super(errorAttributes, errorViewResolvers);
    }

    @RequestMapping
    public JsonResponse<Object> error() {
        return JsonResponse.error();
    }
}
