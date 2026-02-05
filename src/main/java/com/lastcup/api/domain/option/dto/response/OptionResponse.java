package com.lastcup.api.domain.option.dto.response;

import com.lastcup.api.domain.option.domain.Option;
import com.lastcup.api.domain.option.domain.OptionCategory;
import com.lastcup.api.domain.option.domain.OptionSelectionType;
import io.swagger.v3.oas.annotations.media.Schema;

public record OptionResponse(
        @Schema(description = "옵션 ID", example = "1")
        Long id,

        @Schema(description = "옵션명", example = "바닐라 시럽")
        String name,

        @Schema(description = "옵션 카테고리", example = "SYRUP")
        OptionCategory category,

        @Schema(description = "옵션 선택 방식", example = "COUNT")
        OptionSelectionType selectionType
) {
    public static OptionResponse from(Option option) {
        return new OptionResponse(
                option.getId(),
                option.getName(),
                option.getCategory(),
                option.getSelectionType()
        );
    }
}
