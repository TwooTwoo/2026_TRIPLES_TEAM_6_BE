package com.lastcup.api.domain.auth.dto.response;

import com.lastcup.api.domain.user.dto.response.LoginType;
import com.lastcup.api.infrastructure.oauth.SocialProvider;
import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResultResponse(
        @Schema(description = "유저 요약")
        UserSummaryResponse user,

        @Schema(description = "인증 토큰")
        AuthTokensResponse tokens,

        @Schema(description = "로그인 타입", example = "LOCAL")
        LoginType loginType,

        @Schema(description = "소셜 로그인 공급자(소셜 로그인인 경우에만 존재)", example = "GOOGLE", nullable = true)
        SocialProvider socialProvider
) {
}
