package com.lastcup.api.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SocialLoginRequest(
        @Schema(description = "소셜 제공자 토큰 (GOOGLE은 ID Token 사용)", example = "eyJhbGciOiJSUzI1NiIsImtpZCI6...")
        String providerAccessToken,
        @Schema(description = "카카오 인가 코드 (KAKAO 로그인 시 사용)", example = "ABCD1234")
        String authorizationCode
) {
}
