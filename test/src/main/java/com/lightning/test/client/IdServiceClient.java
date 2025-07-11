package com.lightning.test.client;

import com.lightning.web.bean.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "idGenerate") // name 是注册到 nacos 的服务名
public interface IdServiceClient {
    @GetMapping("/idGenerate")
    ResponseResult generateId();
}
