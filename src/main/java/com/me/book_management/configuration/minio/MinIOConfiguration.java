package com.me.book_management.configuration.minio;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfiguration {

    @Value("${spring.minio.url}")
    private String minioUrl;
    @Value("${spring.minio.bucket}")
    private String minioBucket;
    @Value("${spring.minio.access-key}")
    private String minioAccessKey;
    @Value("${spring.minio.secret-key}")
    private String minioSecretKey;

    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        MinioClient minioClient = new MinioClient(
            minioUrl,
                minioAccessKey,
                minioSecretKey
        );
        return minioClient;
    }
}
