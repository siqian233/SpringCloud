package com.lightning.idgenerator;

import com.lightning.idgenerator.util.SnowflakeIdGenerator;
import com.lightning.web.bean.ResponseResult;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
public class IdGeneratorController {

    private final SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1, 1);

    @GetMapping("/idGenerate")
    public ResponseResult generate() {
        return ResponseResult.ok().setData(generator.nextId());
    }
}
