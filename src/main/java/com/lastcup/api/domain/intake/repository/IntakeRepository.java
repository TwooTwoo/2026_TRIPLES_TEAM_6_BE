package com.lastcup.api.domain.intake.repository;

import com.lastcup.api.domain.intake.domain.Intake;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntakeRepository extends JpaRepository<Intake, Long> {

    List<Intake> findByUserIdAndIntakeDateOrderByCreatedAtDesc(Long userId, LocalDate intakeDate);
}
