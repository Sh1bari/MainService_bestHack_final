package com.example.mainservice.services;


import com.example.mainservice.exceptions.GeneralException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {
    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    private final MinioClient minioClient;

    public String uploadFile(MultipartFile file){
        String objectName = generateObjectName(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());
            return objectName;
        } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            throw new GeneralException(409, "Cant save file");
        }
    }
    private String generateObjectName(String originalName) {
        return UUID.randomUUID() + "_" + originalName;
    }
}
