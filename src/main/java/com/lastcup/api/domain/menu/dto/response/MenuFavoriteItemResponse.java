
package com.lastcup.api.domain.menu.dto.response;

import com.lastcup.api.domain.menu.domain.MenuCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "메뉴 즐겨찾기 아이템")
public record MenuFavoriteItemResponse(
        @Schema(description = "메뉴 ID", example = "10")
        Long id,

        @Schema(description = "브랜드 ID", example = "1")
        Long brandId,

        @Schema(description = "브랜드명", example = "스타벅스")
        String brandName,

        @Schema(description = "메뉴명", example = "카페 라떼")
        String name,

        @Schema(description = "카테고리", example = "COFFEE")
        MenuCategory category,

        @Schema(description = "이미지 URL", example = "https://...")
        String imageUrl,

        @Schema(description = "즐겨찾기 여부", example = "true")
        boolean isFavorite
) {
}
