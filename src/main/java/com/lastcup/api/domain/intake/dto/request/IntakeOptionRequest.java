package com.lastcup.api.domain.intake.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "섭취 옵션 요청")
public record IntakeOptionRequest(

        @Schema(description = "옵션 ID", example = "5")
        @NotNull
        Long optionId,

        @Schema(description = "옵션 수량", example = "2")
        @NotNull
        @Min(1)
        Integer quantity
) {
}
