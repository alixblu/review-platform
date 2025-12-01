package com.example.productservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
@Slf4j
public class S3Config {

	@Bean(name = "s3Client")
	public S3Client s3Client(
			@Value("${s3.region}") String region,
			@Value("${aws.accessKeyId:${AWS_ACCESS_KEY_ID:${AWS_ACCESS_KEY:}}}") String accessKeyId,
			@Value("${aws.secretAccessKey:${AWS_SECRET_ACCESS_KEY:${AWS_SECRET_KEY:}}}") String secretAccessKey
	) {
		S3ClientBuilder builder = S3Client.builder().region(Region.of(region));

		if (accessKeyId != null && !accessKeyId.isBlank() && secretAccessKey != null && !secretAccessKey.isBlank()) {
			String masked = accessKeyId.length() > 4 ? accessKeyId.substring(0, 4) + "****" : "****";
			log.info("S3Config: Using STATIC AWS credentials (accessKeyId={}...)", masked);
			builder = builder.credentialsProvider(
					software.amazon.awssdk.auth.credentials.StaticCredentialsProvider.create(
							software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create(accessKeyId, secretAccessKey)
					)
			);
		} else {
			log.info("S3Config: Using DEFAULT AWS credentials provider chain");
			builder = builder.credentialsProvider(software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider.create());
		}

		return builder.build();
	}

	@Bean(name = "knowledgeBaseS3Client")
	public S3Client knowledgeBaseS3Client(
			@Value("${s3.knowledgebase.region}") String region,
			@Value("${aws.kb.accessKeyId:}") String accessKeyId,
			@Value("${aws.kb.secretAccessKey:}") String secretAccessKey
	) {
		log.info("KnowledgeBase S3Config: region={}, accessKeyId present={}, secretAccessKey present={}", 
				region, 
				accessKeyId != null && !accessKeyId.isBlank(), 
				secretAccessKey != null && !secretAccessKey.isBlank());
		
		S3ClientBuilder builder = S3Client.builder().region(Region.of(region));
		
		if (accessKeyId != null && !accessKeyId.isBlank() && secretAccessKey != null && !secretAccessKey.isBlank()) {
			String masked = accessKeyId.length() > 4 ? accessKeyId.substring(0, 4) + "****" : "****";
			log.info("KnowledgeBase S3Config: Using STATIC AWS credentials (accessKeyId={}...)", masked);
			builder = builder.credentialsProvider(
					software.amazon.awssdk.auth.credentials.StaticCredentialsProvider.create(
							software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create(accessKeyId, secretAccessKey)
					)
			);
		} else {
			log.warn("KnowledgeBase S3Config: Credentials not found! Using DEFAULT AWS credentials provider chain");
			builder = builder.credentialsProvider(software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider.create());
		}
		
		return builder.build();
	}
}

