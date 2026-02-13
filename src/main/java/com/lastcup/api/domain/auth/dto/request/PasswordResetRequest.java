package com.lastcup.api.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest(
        @Schema(description = "비밀번호 재설정 요청 아이디", example = "lastcup_user")
        @NotBlank
        String loginId,

        @Schema(description = "비밀번호 재설정 요청 이메일", example = "user@example.com")
        @NotBlank
        @Email
        String email
) {
}
