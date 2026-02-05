package com.lastcup.api.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordResetConfirmRequest(
        @Schema(description = "비밀번호 재설정 토큰", example = "uuid-token")
        @NotBlank
        String token,

        @Schema(description = "새 비밀번호", example = "password123!")
        @NotBlank
        @Size(min = 8, max = 100)
        String newPassword
) {
}
