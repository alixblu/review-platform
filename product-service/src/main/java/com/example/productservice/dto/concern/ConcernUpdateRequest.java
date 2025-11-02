package com.example.productservice.dto.concern;

import com.example.commonlib.enums.ConcernTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConcernUpdateRequest {

    @Schema(description = "Loại mối quan tâm (ví dụ: ACNE, AGING, DARK_SPOTS...)")
    private ConcernTypeEnum type;

    @Schema(description = "Mô tả chi tiết mối quan tâm về da")
    private String description;

    @Schema(description = "Những điều cần tránh")
    private String avoid;

    @Schema(description = "Những điều nên làm hoặc sản phẩm khuyên dùng")
    private String recommend;
}
