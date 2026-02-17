package com.lastcup.api.domain.menu.mapper;

import com.lastcup.api.domain.brand.domain.Brand;
import com.lastcup.api.domain.menu.domain.Menu;
import com.lastcup.api.domain.menu.domain.MenuSize;
import com.lastcup.api.domain.menu.dto.response.MenuFavoriteItemResponse;
import com.lastcup.api.domain.menu.dto.response.MenuListItemResponse;
import com.lastcup.api.domain.menu.dto.response.MenuSearchResponse;
import com.lastcup.api.domain.menu.dto.response.MenuSizeResponse;
import com.lastcup.api.domain.menu.dto.response.NutritionResponse;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public MenuListItemResponse toMenuListItem(Menu menu, boolean isFavorite) {
        return new MenuListItemResponse(
                menu.getId(),
                menu.getName(),
                menu.getCategory(),
                menu.getImageUrl(),
                isFavorite
        );
    }

    public MenuSearchResponse toMenuSearch(Menu menu, boolean isFavorite) {
        Brand brand = menu.getBrand();
        return new MenuSearchResponse(
                menu.getId(),
                brand.getName(),
                menu.getName(),
                menu.getImageUrl(),
                isFavorite
        );
    }

    public MenuSizeResponse toMenuSize(MenuSize menuSize) {
        return new MenuSizeResponse(
                menuSize.getId(),
                menuSize.getSizeName(),
                menuSize.getVolumeMl(),
                toNutrition(menuSize)
        );
    }

    private NutritionResponse toNutrition(MenuSize menuSize) {
        return NutritionResponse.from(menuSize.getNutrition());
    }

    public MenuFavoriteItemResponse toMenuFavoriteItem(Menu menu) {
        Brand brand = menu.getBrand();
        return new MenuFavoriteItemResponse(
                menu.getId(),
                brand.getId(),
                brand.getName(),
                menu.getName(),
                menu.getCategory(),
                menu.getImageUrl(),
                true
        );
    }
}
