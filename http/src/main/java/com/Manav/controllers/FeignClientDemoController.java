package com.Manav.controllers;

import com.Manav.clients.DemoFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FeignClientDemoController {
    @Autowired
    private DemoFeignClient demoFeignClient;

    @GetMapping("/getWithFeignTemplate/{name}")
    public String helloWorldWithName(@PathVariable String name){
        return demoFeignClient.helloWorldWithName(name);
    }
}
