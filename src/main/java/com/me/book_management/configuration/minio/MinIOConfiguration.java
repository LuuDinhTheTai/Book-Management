package com.me.book_management.configuration.minio;

import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
import com.jlefebure.spring.boot.minio.MinioService;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    public MinioService minioService() {
        return new MinioService();
    }

    @Bean
    public MinioConfigurationProperties minioConfigurationProperties() {
        return new MinioConfigurationProperties();
    }

    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(
                minioUrl,
                minioAccessKey,
                minioSecretKey
        );
    }
}
