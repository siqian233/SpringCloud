package com.lightning.hello.web.controller;

import com.lightning.web.bean.ResponseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Tag(name = "hello",description = "显示不同语言的hello")
public class HelloController {
    @Autowired
    private Random random;

    @GetMapping("hello")
    public ResponseResult hello() {
        return ResponseResult.ok("233");
    }


    @GetMapping("/en")
    public ResponseResult hello1() {
        return ResponseResult.ok("hello");
    }

    @GetMapping("/zh")
    public ResponseResult hello2() {
        return ResponseResult.ok("你好");
    }

    @GetMapping("/jp")
    public ResponseResult hello3() {
        return ResponseResult.ok("こんにちは");//日语你好
    }
}
