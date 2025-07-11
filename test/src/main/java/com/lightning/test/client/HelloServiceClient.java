package com.lightning.test.client;

import com.lightning.web.bean.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hello") // name 是注册到 nacos 的服务名
public interface HelloServiceClient {
    @GetMapping("/hello")
    ResponseResult hello();
}
