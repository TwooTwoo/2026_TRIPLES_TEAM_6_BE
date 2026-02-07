package com.lastcup.api.domain.auth.domain;

import com.lastcup.api.global.config.BaseTimeEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "password_reset_tokens",
        indexes = {
                @Index(name = "idx_password_reset_tokens_user_id", columnList = "user_id"),
                @Index(name = "idx_password_reset_tokens_token", columnList = "token")
        }
)
public class PasswordResetToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true, length = 100)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime usedAt;

    protected PasswordResetToken() {
    }

    private PasswordResetToken(Long userId, String token, LocalDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public static PasswordResetToken create(Long userId, String token, LocalDateTime expiresAt) {
        validate(userId, token, expiresAt);
        return new PasswordResetToken(userId, token, expiresAt);
    }

    private static void validate(Long userId, String token, LocalDateTime expiresAt) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("token is blank");
        }
        if (expiresAt == null) {
            throw new IllegalArgumentException("expiresAt is null");
        }
    }

    public void use(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public boolean isExpired(LocalDateTime now) {
        return expiresAt.isBefore(now);
    }

    public boolean isUsed() {
        return usedAt != null;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }
}
