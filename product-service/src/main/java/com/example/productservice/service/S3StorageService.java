package com.example.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService {

	private final S3Client s3Client;

	@Value("${s3.bucket}")
	private String bucket;

	@Value("${s3.region}")
	private String region;

	/**
	 * Optional CDN or static website base URL. If set, returned URLs will use this base.
	 * Example: https://cdn.example.com
	 */
	@Value("${s3.publicBaseUrl:}")
	private String publicBaseUrl;

	public String uploadFile(MultipartFile file, String folder) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File must not be empty");
		}

		String originalName = Objects.requireNonNullElse(file.getOriginalFilename(), "file");
		String extension = Optional.ofNullable(originalName)
				.filter(name -> name.contains("."))
				.map(name -> name.substring(name.lastIndexOf(".")))
				.orElse("");

		String normalizedFolder = (folder == null) ? "" : folder.trim().replaceAll("^/+|/+$", "");
		String keyPrefix = normalizedFolder.isEmpty() ? "" : normalizedFolder + "/";
		String key = keyPrefix + UUID.randomUUID() + extension;

		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
					.bucket(bucket)
					.key(key)
					.contentType(file.getContentType())
					.build();

			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read file content", e);
		}

		if (publicBaseUrl != null && !publicBaseUrl.isBlank()) {
			return publicBaseUrl.replaceAll("/+$", "") + "/" + key;
		}
		return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
	}

	public void deleteByUrlOrKey(String urlOrKey) {
		if (urlOrKey == null || urlOrKey.isBlank()) {
			throw new IllegalArgumentException("urlOrKey must not be blank");
		}
		String key = toKey(urlOrKey);
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.build();
		s3Client.deleteObject(deleteObjectRequest);
		log.info("Deleted S3 object: {}", key);
	}

	private String toKey(String urlOrKey) {
		String input = urlOrKey.trim();
		// If publicBaseUrl is configured and matches, strip it
		if (publicBaseUrl != null && !publicBaseUrl.isBlank()) {
			String base = publicBaseUrl.replaceAll("/+$", "");
			if (input.startsWith(base + "/")) {
				return input.substring((base + "/").length());
			}
		}
		// Handle typical S3 URL: https://bucket.s3.region.amazonaws.com/key...
		String s3Prefix = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
		if (input.startsWith(s3Prefix)) {
			return input.substring(s3Prefix.length());
		}
		// If it's a plain key, return as-is
		return input.replaceAll("^/+",""); // normalize leading slash
	}
}


