package com.oak.gateway.fallback;

import com.oak.gateway.common.response.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容断处理类
 *
 * @author: chippy
 * @datetime 2020-12-31 14:13
 */
@RestController
public class FallbackController {

    @GetMapping(value = "/fallback")
    public Result<String> fallback() {
        return Result.fail(102, "下游服务暂不可用");
    }

}
