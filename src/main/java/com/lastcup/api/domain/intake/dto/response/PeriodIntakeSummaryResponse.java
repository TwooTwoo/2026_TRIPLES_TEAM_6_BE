package com.lastcup.api.domain.intake.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "기간별 섭취 요약 응답")
public record PeriodIntakeSummaryResponse(

        @Schema(description = "시작 날짜", example = "2026-01-08")
        LocalDate startDate,

        @Schema(description = "종료 날짜", example = "2026-01-13")
        LocalDate endDate,

        @Schema(description = "총 카페인 섭취량(mg)", example = "1268")
        int totalCaffeine,

        @Schema(description = "총 당류 섭취량(g)", example = "932")
        int totalSugar,

        @Schema(description = "총 잔 수", example = "34")
        int intakeCount,

        @Schema(description = "섭취 이력 목록")
        List<IntakeHistoryItemResponse> intakes
) {
}
