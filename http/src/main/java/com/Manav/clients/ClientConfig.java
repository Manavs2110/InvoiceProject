package com.Manav.clients;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("client-config")
@AllArgsConstructor
@NoArgsConstructor
public class ClientConfig {
    private String baseUrl;
}
