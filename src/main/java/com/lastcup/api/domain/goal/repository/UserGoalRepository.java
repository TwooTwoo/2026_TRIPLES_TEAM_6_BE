package com.lastcup.api.domain.goal.repository;

import com.lastcup.api.domain.goal.domain.UserGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGoalRepository extends JpaRepository<UserGoal, Long> {
}
