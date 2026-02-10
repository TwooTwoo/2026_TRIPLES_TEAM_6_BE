package com.lastcup.api.domain.intake.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "음료 종류별 통계 항목")
public record DrinkGroupResponse(

        @Schema(description = "브랜드명", example = "스타벅스")
        String brandName,

        @Schema(description = "메뉴명", example = "블랙 글레이즈드 라떼")
        String menuName,

        @Schema(description = "온도", example = "ICED")
        String temperature,

        @Schema(description = "사이즈명", example = "Tall")
        String sizeName,

        @Schema(description = "추가 옵션 목록")
        List<IntakeOptionDetailResponse> options,

        @Schema(description = "총 잔 수", example = "20")
        int totalQuantity,

        @Schema(description = "1잔당 카페인(mg)", example = "80")
        int caffeinePerUnit,

        @Schema(description = "1잔당 당류(g)", example = "9")
        int sugarPerUnit
) {
}
