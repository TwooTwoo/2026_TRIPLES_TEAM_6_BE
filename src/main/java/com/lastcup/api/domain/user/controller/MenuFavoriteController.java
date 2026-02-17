
package com.lastcup.api.domain.user.controller;

import com.lastcup.api.domain.user.dto.response.MenuFavoriteResponse;
import com.lastcup.api.domain.user.dto.response.MenuUnfavoriteResponse;
import com.lastcup.api.domain.user.service.MenuFavoriteService;
import com.lastcup.api.global.response.ApiResponse;
import com.lastcup.api.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MenuFavorite", description = "메뉴 즐겨찾기 API")
@RestController
@RequestMapping("/api/v1/menus/{menuId}/favorites")
public class MenuFavoriteController {

    private final MenuFavoriteService menuFavoriteService;

    public MenuFavoriteController(MenuFavoriteService menuFavoriteService) {
        this.menuFavoriteService = menuFavoriteService;
    }

    @Operation(summary = "메뉴 즐겨찾기 등록", description = "해당 메뉴를 즐겨찾기에 등록합니다.")
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping
    public ApiResponse<MenuFavoriteResponse> createMenuFavorite(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long menuId
    ) {
        menuFavoriteService.createMenuFavorite(authUser.userId(), menuId);
        return ApiResponse.success(MenuFavoriteResponse.success());
    }

    @Operation(summary = "메뉴 즐겨찾기 취소", description = "해당 메뉴를 즐겨찾기에서 삭제합니다.")
    @SecurityRequirement(name = "BearerAuth")
    @DeleteMapping
    public ApiResponse<MenuUnfavoriteResponse> deleteMenuFavorite(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long menuId
    ) {
        menuFavoriteService.deleteMenuFavorite(authUser.userId(), menuId);
        return ApiResponse.success(MenuUnfavoriteResponse.success());
    }
}
