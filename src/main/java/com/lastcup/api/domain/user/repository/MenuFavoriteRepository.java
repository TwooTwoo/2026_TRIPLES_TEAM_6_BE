
package com.lastcup.api.domain.user.repository;

import com.lastcup.api.domain.user.domain.MenuFavorite;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuFavoriteRepository extends JpaRepository<MenuFavorite, Long> {

    @Query("SELECT mf.menuId FROM MenuFavorite mf WHERE mf.userId = :userId")
    List<Long> findMenuIdsByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndMenuId(Long userId, Long menuId);

    void deleteByUserIdAndMenuId(Long userId, Long menuId);
}
