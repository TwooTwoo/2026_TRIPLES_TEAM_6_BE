package com.lastcup.api.domain.intake.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "일별 섭취 요약 응답")
public record DailyIntakeSummaryResponse(

        @Schema(description = "조회 날짜", example = "2026-01-26")
        LocalDate date,

        @Schema(description = "총 카페인 섭취량(mg)", example = "189")
        int totalCaffeine,

        @Schema(description = "총 당류 섭취량(g)", example = "49")
        int totalSugar,

        @Schema(description = "카페인 목표(mg)", example = "400")
        int goalCaffeine,

        @Schema(description = "당류 목표(g)", example = "25")
        int goalSugar,

        @Schema(description = "총 잔 수", example = "2")
        int intakeCount,

        @Schema(description = "섭취 이력 목록")
        List<IntakeHistoryItemResponse> intakes
) {
}
