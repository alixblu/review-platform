package com.example.postservice.dto.like;

// Phản hồi cho client biết trạng thái mới và tổng số like mới
public record LikeToggleResponse(
        boolean liked, // true = đã like, false = đã unlike
        long newLikeCount
) {}