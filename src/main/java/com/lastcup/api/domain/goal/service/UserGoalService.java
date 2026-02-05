package com.lastcup.api.domain.goal.service;

import com.lastcup.api.domain.goal.domain.UserGoal;
import com.lastcup.api.domain.goal.repository.UserGoalRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserGoalService {

    private static final int DEFAULT_DAILY_CAFFEINE_TARGET = 400;
    private static final int DEFAULT_DAILY_SUGAR_TARGET = 50;

    private final UserGoalRepository repository;

    public UserGoalService(UserGoalRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public UserGoal findCurrent(Long userId) {
        return repository.findTopByUserIdAndEndDateIsNullOrderByStartDateDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("user goal not found"));
    }

    public UserGoal findOrCreateCurrent(Long userId) {
        return repository.findTopByUserIdAndEndDateIsNullOrderByStartDateDesc(userId)
                .orElseGet(() -> createDefault(userId, LocalDate.now()));
    }

    public UserGoal updateGoal(
            Long userId,
            int dailyCaffeineTarget,
            int dailySugarTarget,
            LocalDate startDate
    ) {
        LocalDate effectiveStartDate = resolveStartDate(startDate);
        UserGoal current = repository.findTopByUserIdAndEndDateIsNullOrderByStartDateDesc(userId)
                .orElse(null);

        if (current == null) {
            return repository.save(UserGoal.create(
                    userId,
                    dailyCaffeineTarget,
                    dailySugarTarget,
                    effectiveStartDate,
                    null
            ));
        }

        if (current.getStartDate().isEqual(effectiveStartDate)) {
            current.updateTargets(dailyCaffeineTarget, dailySugarTarget);
            return current;
        }

        if (effectiveStartDate.isBefore(current.getStartDate())) {
            throw new IllegalArgumentException("startDate is before current startDate");
        }

        current.close(effectiveStartDate.minusDays(1));
        UserGoal next = UserGoal.create(
                userId,
                dailyCaffeineTarget,
                dailySugarTarget,
                effectiveStartDate,
                null
        );
        return repository.save(next);
    }

    private UserGoal createDefault(Long userId, LocalDate startDate) {
        UserGoal created = UserGoal.create(
                userId,
                DEFAULT_DAILY_CAFFEINE_TARGET,
                DEFAULT_DAILY_SUGAR_TARGET,
                startDate,
                null
        );
        return repository.save(created);
    }

    private LocalDate resolveStartDate(LocalDate startDate) {
        return startDate != null ? startDate : LocalDate.now();
    }
}
