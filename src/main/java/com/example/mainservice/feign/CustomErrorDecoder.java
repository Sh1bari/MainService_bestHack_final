package com.example.mainservice.feign;

import com.example.mainservice.exceptions.GlobalAppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Vladimir Krasnov
 */
@RequiredArgsConstructor
@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus statusCode = HttpStatus.valueOf(response.status());
        if (!statusCode.is2xxSuccessful()) {
            return FeignException.errorStatus(methodKey, response);
        }
        return null;
    }

}
