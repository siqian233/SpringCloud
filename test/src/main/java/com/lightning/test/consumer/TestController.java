package com.lightning.test.consumer;

import com.lightning.test.client.HelloServiceClient;
import com.lightning.test.client.IdServiceClient;
import com.lightning.web.bean.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final HelloServiceClient helloService;
    private final IdServiceClient idService;

    public TestController(HelloServiceClient helloService, IdServiceClient idService) {
        this.helloService = helloService;
        this.idService = idService;
    }

    @GetMapping("/test")
    public ResponseResult test() {
        ResponseResult hello = helloService.hello();
        ResponseResult id = idService.generateId();
        if(hello.getCode() != 1 || id.getCode() != 1){
            return ResponseResult.error("调用服务失败").setData(hello.getData() + hello.getMsg() + " | " + id.getData() + id.getMsg());
        }
        return ResponseResult.ok().setData(hello.getMsg() + " | " + id.getData());
    }
}