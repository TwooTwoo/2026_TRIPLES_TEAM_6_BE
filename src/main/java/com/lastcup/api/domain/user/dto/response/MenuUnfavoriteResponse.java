package com.lastcup.api.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MenuUnfavoriteResponse(
        @Schema(description = "즐겨찾기 삭제 성공 여부", example = "true")
        boolean unfavorited
) {
    public static MenuUnfavoriteResponse success() {
        return new MenuUnfavoriteResponse(true);
    }
}
