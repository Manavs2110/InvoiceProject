package com.Manav.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name="client-service",
        configuration = ClientConfig.class,
        url="${client-config.base-url}"
)
public interface DemoFeignClient {
    @GetMapping(value="/hello/{name}",produces = "application/json")
    String helloWorldWithName(@PathVariable("name") String name);
}
