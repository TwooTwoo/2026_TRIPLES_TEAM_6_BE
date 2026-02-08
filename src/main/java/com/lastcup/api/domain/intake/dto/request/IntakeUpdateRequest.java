package com.lastcup.api.domain.intake.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * 섭취 기록 수정 요청.
 * 생성(Create)과 달리 모든 필드를 필수로 요구한다.
 * → 날짜·수량이 의도치 않게 기본값으로 덮어써지는 것을 방지하기 위함.
 */
@Schema(description = "섭취 기록 수정 요청")
public record IntakeUpdateRequest(

        @Schema(description = "메뉴 사이즈 ID", example = "42")
        @NotNull
        Long menuSizeId,

        @Schema(description = "섭취 날짜(YYYY-MM-DD)", example = "2026-01-24")
        @NotNull
        LocalDate intakeDate,

        @Schema(description = "수량", example = "1")
        @NotNull
        @Min(1)
        Integer quantity,

        @Schema(description = "추가 옵션 목록")
        @Valid
        List<IntakeOptionRequest> options
) {
}
