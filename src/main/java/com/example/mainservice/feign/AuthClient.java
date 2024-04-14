package com.example.mainservice.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Vladimir Krasnov
 */
@Primary
@FeignClient(name = "auth-server", configuration = FeignConfig.class)
public interface AuthClient {
    @GetMapping("/api/auth/")
    ResponseEntity<String> greeting();
}
