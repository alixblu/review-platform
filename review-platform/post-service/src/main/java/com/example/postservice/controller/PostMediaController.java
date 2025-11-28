package com.example.postservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.postservice.service.PostMediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts/media")
@RequiredArgsConstructor
@Slf4j
public class PostMediaController {

    private final PostMediaService postMediaService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<List<String>>> uploadPostMedia(
            @RequestParam("files") List<MultipartFile> files) {
        
        log.info("Uploading {} files for post", files.size());
        List<String> mediaUrls = postMediaService.uploadPostMedia(files);
        
        return ResponseEntity.ok(
            new ApiResponse<>("Media uploaded successfully", mediaUrls)
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteMedia(@RequestParam String url) {
        log.info("Deleting media: {}", url);
        postMediaService.deleteMedia(url);
        
        return ResponseEntity.ok(
            new ApiResponse<>("Media deleted successfully", null)
        );
    }
}
