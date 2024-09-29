package com.Manav.controllers;

import com.Manav.clients.ClientConfig;
import com.Manav.dto.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class RestTempateDemoController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientConfig clientConfig;

    @GetMapping("/getWithRestTemplate/{name}")
    public String helloWorldWithName(@PathVariable String name){
        String baseUrl = clientConfig.getBaseUrl();
        String requestPath = "/hello/{name}";
        return restTemplate.getForEntity(baseUrl+requestPath,String.class,name).getBody();

    }
    @GetMapping("/postWithRestTemplate/{invoiceNumber}")
    public Invoice getInvoiceNumberWithRestTemplate(@PathVariable String invoiceNumber){
        String baseUrl = clientConfig.getBaseUrl();
        String requestPath = "/UpdateInvoice";
        return restTemplate.postForEntity(baseUrl+requestPath,
                Invoice.builder().invoiceNumber(invoiceNumber).build(),Invoice.class).getBody();
    }
}
