package com.example.mainservice.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Vladimir Krasnov
 */
@Primary
@FeignClient(name = "auth-server", configuration = FeignConfig.class)
@EnableHystrix
public interface AuthClient {
    @GetMapping("/api/auth/")
    @HystrixCommand(fallbackMethod = "serviceUnavailable()")
    ResponseEntity<String> greeting();

    private ResponseEntity<String> serviceUnavailable() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .build();
    }
}
