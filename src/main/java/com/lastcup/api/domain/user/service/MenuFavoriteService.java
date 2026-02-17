
package com.lastcup.api.domain.user.service;

import com.lastcup.api.domain.user.domain.MenuFavorite;
import com.lastcup.api.domain.user.repository.MenuFavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuFavoriteService {

    private final MenuFavoriteRepository menuFavoriteRepository;

    public MenuFavoriteService(MenuFavoriteRepository menuFavoriteRepository) {
        this.menuFavoriteRepository = menuFavoriteRepository;
    }

    @Transactional
    public void createMenuFavorite(Long userId, Long menuId) {
        validateIds(userId, menuId);

        if (menuFavoriteRepository.existsByUserIdAndMenuId(userId, menuId)) {
            return;
        }

        MenuFavorite favorite = MenuFavorite.create(userId, menuId);
        menuFavoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteMenuFavorite(Long userId, Long menuId) {
        validateIds(userId, menuId);

        menuFavoriteRepository.deleteByUserIdAndMenuId(userId, menuId);
    }

    private void validateIds(Long userId, Long menuId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId is invalid");
        }
        if (menuId == null || menuId <= 0) {
            throw new IllegalArgumentException("menuId is invalid");
        }
    }
}
