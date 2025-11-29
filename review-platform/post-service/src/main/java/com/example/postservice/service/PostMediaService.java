package com.example.postservice.service;

import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostMediaService {

    private final S3Client s3Client;

    @Value("${s3.bucket}")
    private String bucket;

    @Value("${s3.region}")
    private String region;

    @Value("${s3.publicBaseUrl:}")
    private String publicBaseUrl;

    private static final int MAX_IMAGES = 3;
    private static final int MAX_VIDEOS = 1;
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final long MAX_VIDEO_SIZE = 50 * 1024 * 1024; // 50MB

    public List<String> uploadPostMedia(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }

        int imageCount = 0;
        int videoCount = 0;
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String contentType = file.getContentType();
            boolean isImage = contentType != null && contentType.startsWith("image/");
            boolean isVideo = contentType != null && contentType.startsWith("video/");

            // Validate file type
            if (!isImage && !isVideo) {
                throw new AppException(ErrorCode.INVALID_REQUEST, "Only images and videos are allowed");
            }

            // Validate counts
            if (isImage) {
                imageCount++;
                if (imageCount > MAX_IMAGES) {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Maximum " + MAX_IMAGES + " images allowed");
                }
                if (file.getSize() > MAX_IMAGE_SIZE) {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Image size must not exceed 5MB");
                }
            }

            if (isVideo) {
                videoCount++;
                if (videoCount > MAX_VIDEOS) {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Maximum " + MAX_VIDEOS + " video allowed");
                }
                if (file.getSize() > MAX_VIDEO_SIZE) {
                    throw new AppException(ErrorCode.INVALID_REQUEST, "Video size must not exceed 50MB");
                }
            }

            // Upload file
            String url = uploadFile(file, "posts");
            uploadedUrls.add(url);
        }

        return uploadedUrls;
    }

    private String uploadFile(MultipartFile file, String folder) {
        String originalName = Objects.requireNonNullElse(file.getOriginalFilename(), "file");
        String extension = "";
        if (originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String key = folder + "/" + UUID.randomUUID() + extension;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            log.info("Uploaded file to S3: {}", key);
        } catch (IOException e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION, "Failed to upload file: " + e.getMessage());
        }

        if (publicBaseUrl != null && !publicBaseUrl.isBlank()) {
            return publicBaseUrl.replaceAll("/+$", "") + "/" + key;
        }
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
    }

    public void deleteMedia(String url) {
        if (url == null || url.isBlank()) return;

        String key = extractKeyFromUrl(url);
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteRequest);
            log.info("Deleted S3 object: {}", key);
        } catch (Exception e) {
            log.error("Failed to delete S3 object: {}", key, e);
        }
    }

    private String extractKeyFromUrl(String url) {
        if (publicBaseUrl != null && !publicBaseUrl.isBlank()) {
            String base = publicBaseUrl.replaceAll("/+$", "");
            if (url.startsWith(base + "/")) {
                return url.substring((base + "/").length());
            }
        }

        String s3Prefix = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        if (url.startsWith(s3Prefix)) {
            return url.substring(s3Prefix.length());
        }

        return url.replaceAll("^/+", "");
    }
}
