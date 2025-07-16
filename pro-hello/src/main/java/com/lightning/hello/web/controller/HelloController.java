package com.lightning.hello.web.controller;

import com.lightning.web.bean.ResponseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Tag(name = "hello",description = "显示不同语言的hello")
public class HelloController {
    @Autowired
    private Random random;

    @GetMapping("/hello")
    public ResponseResult hello() {
        String str ;
        int i = random.nextInt(3);
        str = switch (i) {
            case 0 -> "hello";
            case 1 -> "你好";
            case 2 -> "こんにちは";
            default -> "";
        };

        return ResponseResult.ok(str);
    }
}
