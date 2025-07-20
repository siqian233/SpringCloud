package com.lightning.idgenerator.web.controller;

import com.lightning.idgenerator.util.SnowflakeIdGenerator;
import com.lightning.web.bean.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdGeneratorController {

    private final SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1, 1);

    @GetMapping("/next")
    public ResponseResult generate() {
        return ResponseResult.ok().setData(generator.nextId());
    }
}
