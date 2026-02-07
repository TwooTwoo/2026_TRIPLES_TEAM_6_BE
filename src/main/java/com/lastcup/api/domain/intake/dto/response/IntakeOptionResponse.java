package com.lastcup.api.domain.intake.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "섭취 옵션 응답")
public record IntakeOptionResponse(

        @Schema(description = "옵션 ID", example = "5")
        Long optionId,

        @Schema(description = "옵션 수량", example = "2")
        int quantity
) {
}
