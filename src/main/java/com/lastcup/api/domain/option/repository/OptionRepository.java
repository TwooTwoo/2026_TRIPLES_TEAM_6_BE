package com.lastcup.api.domain.option.repository;

import com.lastcup.api.domain.option.domain.Option;
import com.lastcup.api.domain.option.domain.OptionCategory;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findByBrandId(Long brandId);

    List<Option> findByBrandIdAndCategory(Long brandId, OptionCategory category);

    long countByIdIn(Collection<Long> ids);
}
