package com.lastcup.api.domain.user.dto.response;

import com.lastcup.api.domain.user.domain.UserStatus;
import com.lastcup.api.infrastructure.oauth.SocialProvider;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserMeResponse(
        @Schema(description = "유저 ID", example = "1")
        Long id,

        @Schema(description = "닉네임", example = "커피러버")
        String nickname,

        @Schema(description = "이메일", example = "user@example.com")
        String email,

        @Schema(description = "프로필 이미지 URL", example = "https://.../profile.jpg")
        String profileImageUrl,

        @Schema(description = "계정 로그인 타입", example = "LOCAL")
        LoginType loginType,

        @Schema(description = "소셜 로그인 공급자(소셜 로그인인 경우에만 존재)", example = "GOOGLE", nullable = true)
        SocialProvider socialProvider,

        @Schema(description = "상태", example = "ACTIVE")
        UserStatus status,

        @Schema(description = "가입일", example = "2026-01-14T10:00:00")
        LocalDateTime createdAt,

        @Schema(description = "수정일", example = "2026-01-14T10:00:00")
        LocalDateTime updatedAt
) {
}
