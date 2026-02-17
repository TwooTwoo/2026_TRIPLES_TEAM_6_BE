
package com.lastcup.api.domain.user.domain;

import com.lastcup.api.global.config.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "menu_favorites",
        uniqueConstraints = @UniqueConstraint(name = "uk_menu_favorites_user_menu", columnNames = {"user_id", "menu_id"})
)
public class MenuFavorite extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    protected MenuFavorite() {
    }

    private MenuFavorite(Long userId, Long menuId) {
        this.userId = userId;
        this.menuId = menuId;
    }

    public static MenuFavorite create(Long userId, Long menuId) {
        validateUserId(userId);
        validateMenuId(menuId);

        return new MenuFavorite(userId, menuId);
    }

    private static void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId is invalid");
        }
    }

    private static void validateMenuId(Long menuId) {
        if (menuId == null || menuId <= 0) {
            throw new IllegalArgumentException("menuId is invalid");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMenuId() {
        return menuId;
    }
}
