package com.lastcup.api.domain.auth.dto.response;

import com.lastcup.api.domain.user.dto.response.LoginType;
import com.lastcup.api.infrastructure.oauth.SocialProvider;
import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponse(
        @Schema(description = "유저 요약")
        UserSummaryResponse user,

        @Schema(description = "인증 토큰")
        AuthTokensResponse tokens,

        @Schema(description = "신규 유저 여부", example = "true")
        boolean isNewUser,

        @Schema(description = "로그인 타입", example = "SOCIAL")
        LoginType loginType,

        @Schema(description = "소셜 로그인 공급자", example = "GOOGLE")
        SocialProvider socialProvider
) {
}
