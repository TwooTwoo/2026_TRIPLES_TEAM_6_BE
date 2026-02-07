package com.lastcup.api.domain.menu.repository;

import com.lastcup.api.domain.menu.domain.Menu;
import com.lastcup.api.domain.menu.domain.MenuCategory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Page<Menu> findByBrandIdAndIsActiveTrue(Long brandId, Pageable pageable);

    Page<Menu> findByBrandIdAndCategoryAndIsActiveTrue(Long brandId, MenuCategory category, Pageable pageable);

    Page<Menu> findByBrandIdAndNameContainingIgnoreCaseAndIsActiveTrue(Long brandId, String keyword, Pageable pageable);

    Page<Menu> findByBrandIdAndCategoryAndNameContainingIgnoreCaseAndIsActiveTrue(
            Long brandId,
            MenuCategory category,
            String keyword,
            Pageable pageable
    );

    Page<Menu> findByNameContainingIgnoreCaseAndIsActiveTrue(String keyword, Pageable pageable);

    @Query("select m from Menu m join fetch m.brand where m.id in :menuIds and m.isActive = true")
    List<Menu> findByIdInWithBrandAndIsActiveTrue(@Param("menuIds") List<Long> menuIds);
}
