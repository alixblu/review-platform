package com.example.userservice.dto.user_preference;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindSimilarRequest {

	@NotNull(message = "Embedding không được null")
	@Size(min = 1536, max = 1536, message = "Embedding phải có đúng 1536 chiều")
	private List<Double> embedding;

	@Builder.Default
	private Integer limit = 10;

	// Optional: loại trừ chính user này nếu cần
	private String currentUserId;
}


