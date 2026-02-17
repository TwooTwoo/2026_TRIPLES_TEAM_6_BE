package com.lastcup.api.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest(

        @Schema(
                description = "소셜 SDK 로그인 후 전달받은 토큰 (GOOGLE/APPLE: ID Token, KAKAO: Access Token)",
                example = "eyJhbGciOiJSUzI1NiIsImtpZCI6..."
        )
        @NotBlank
        String providerToken,

        @Schema(
                description = "이메일 (APPLE 최초 로그인 시 선택 전달)",
                example = "user@example.com"
        )
        String email
) {
}
