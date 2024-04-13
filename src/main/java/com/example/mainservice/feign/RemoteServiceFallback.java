package com.example.mainservice.feign;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Krasnov
 */
@Component
public class RemoteServiceFallback implements AuthClient {

    @Override
    public ResponseEntity<String> greeting() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .build();
    }
}
