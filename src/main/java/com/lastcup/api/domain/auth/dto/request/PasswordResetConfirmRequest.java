package com.lastcup.api.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordResetConfirmRequest(
        @Schema(description = "비밀번호 재설정 요청 아이디", example = "lastcup_user")
        @NotBlank
        String loginId,

        @Schema(description = "비밀번호 재설정 요청 이메일", example = "user@example.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "이메일로 받은 인증 코드", example = "EC5GZ")
        @NotBlank
        @Size(min = 5, max = 5)
        String verificationCode,

        @Schema(description = "새 비밀번호", example = "password123!")
        @NotBlank
        @Size(min = 8, max = 100)
        String newPassword
) {
}
