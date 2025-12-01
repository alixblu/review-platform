package com.example.productservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.productservice.service.S3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@CrossOrigin(origins = "${FRONTEND_URL:http://localhost:3000}", allowCredentials = "true")
public class UploadController {

	private static final Set<String> ALLOWED_TYPES = Set.of("product", "brand", "review");

	private final S3StorageService s3StorageService;

	@PostMapping(value = "/{type}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<Map<String, String>>> upload(
			@PathVariable String type,
			@RequestPart("file") MultipartFile file
	) {
		if (!ALLOWED_TYPES.contains(type)) throw new AppException(ErrorCode.INVALID_INPUT, "Invalid img type");


		String url = s3StorageService.uploadFile(file, type);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("Uploaded successfully", Map.of("url", url)));
	}


	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> delete(@RequestParam("url") String url) {
		s3StorageService.deleteByUrlOrKey(url);
		return ResponseEntity.ok(new ApiResponse<>("Deleted successfully", null));
	}
}


