package com.lastcup.api.domain.intake.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "섭취 기록 생성 요청")
public record CreateIntakeRequest(

        @Schema(description = "메뉴 사이즈 ID", example = "42")
        @NotNull
        Long menuSizeId,

        @Schema(description = "섭취 날짜(YYYY-MM-DD). 없으면 오늘", example = "2026-01-24")
        LocalDate intakeDate,

        @Schema(description = "수량(기본 1)", example = "1")
        @Min(1)
        Integer quantity,

        @Schema(description = "추가 옵션 목록")
        @Valid
        List<IntakeOptionRequest> options
) {
}
