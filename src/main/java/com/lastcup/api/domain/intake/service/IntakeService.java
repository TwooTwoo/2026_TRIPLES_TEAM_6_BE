package com.lastcup.api.domain.intake.service;

import com.lastcup.api.domain.goal.domain.UserGoal;
import com.lastcup.api.domain.goal.service.UserGoalService;
import com.lastcup.api.domain.intake.domain.Intake;
import com.lastcup.api.domain.intake.dto.request.CreateIntakeRequest;
import com.lastcup.api.domain.intake.dto.request.IntakeOptionRequest;
import com.lastcup.api.domain.intake.dto.response.IntakeOptionResponse;
import com.lastcup.api.domain.intake.dto.response.IntakeResponse;
import com.lastcup.api.domain.intake.repository.IntakeRepository;
import com.lastcup.api.domain.menu.domain.MenuSize;
import com.lastcup.api.domain.menu.domain.Nutrition;
import com.lastcup.api.domain.menu.repository.MenuSizeRepository;
import com.lastcup.api.domain.option.repository.OptionRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IntakeService {

    private final IntakeRepository intakeRepository;
    private final MenuSizeRepository menuSizeRepository;
    private final OptionRepository optionRepository;
    private final UserGoalService userGoalService;

    public IntakeService(
            IntakeRepository intakeRepository,
            MenuSizeRepository menuSizeRepository,
            OptionRepository optionRepository,
            UserGoalService userGoalService
    ) {
        this.intakeRepository = intakeRepository;
        this.menuSizeRepository = menuSizeRepository;
        this.optionRepository = optionRepository;
        this.userGoalService = userGoalService;
    }

    public IntakeResponse createIntake(Long userId, CreateIntakeRequest request) {
        LocalDate intakeDate = resolveIntakeDate(request.intakeDate());
        int quantity = resolveQuantity(request.quantity());

        MenuSize menuSize = findMenuSizeWithNutrition(request.menuSizeId());
        UserGoal goal = userGoalService.findByDate(userId, intakeDate);
        Nutrition nutrition = menuSize.getNutrition();

        validateOptions(request.options());

        Intake intake = Intake.create(
                userId,
                intakeDate,
                menuSize.getId(),
                quantity,
                multiplyOrZero(nutrition.getCaffeineMg(), quantity),
                multiplyOrZero(nutrition.getSugarG(), quantity),
                multiplyNullable(nutrition.getCalories(), quantity),
                multiplyNullable(nutrition.getSodiumMg(), quantity),
                multiplyNullable(nutrition.getProteinG(), quantity),
                multiplyNullable(nutrition.getFatG(), quantity),
                goal.getDailyCaffeineTarget(),
                goal.getDailySugarTarget()
        );

        addOptions(intake, request.options());

        Intake saved = intakeRepository.save(intake);
        return toResponse(saved);
    }

    private MenuSize findMenuSizeWithNutrition(Long menuSizeId) {
        return menuSizeRepository.findDetailById(menuSizeId)
                .orElseThrow(() -> new IllegalArgumentException("MenuSize not found: " + menuSizeId));
    }

    private void validateOptions(List<IntakeOptionRequest> options) {
        if (options == null || options.isEmpty()) {
            return;
        }

        Set<Long> optionIds = options.stream()
                .map(IntakeOptionRequest::optionId)
                .collect(Collectors.toSet());

        long existingCount = optionRepository.countByIdIn(optionIds);
        if (existingCount != optionIds.size()) {
            throw new IllegalArgumentException("Option not found");
        }
    }

    private void addOptions(Intake intake, List<IntakeOptionRequest> options) {
        if (options == null || options.isEmpty()) {
            return;
        }
        for (IntakeOptionRequest option : options) {
            intake.addOption(option.optionId(), option.quantity());
        }
    }

    private IntakeResponse toResponse(Intake intake) {
        List<IntakeOptionResponse> optionResponses = intake.getIntakeOptions().stream()
                .map(o -> new IntakeOptionResponse(o.getOptionId(), o.getQuantity()))
                .toList();

        return new IntakeResponse(
                intake.getId(),
                intake.getUserId(),
                intake.getIntakeDate(),
                intake.getMenuSizeId(),
                intake.getQuantity(),
                intake.getCaffeineSnapshot(),
                intake.getSugarSnapshot(),
                intake.getCaloriesSnapshot(),
                intake.getSodiumSnapshot(),
                intake.getProteinSnapshot(),
                intake.getFatSnapshot(),
                intake.getGoalCaffeineTargetSnapshot(),
                intake.getGoalSugarTargetSnapshot(),
                optionResponses,
                intake.getCreatedAt()
        );
    }

    private LocalDate resolveIntakeDate(LocalDate intakeDate) {
        return intakeDate != null ? intakeDate : LocalDate.now();
    }

    private int resolveQuantity(Integer quantity) {
        return quantity != null ? quantity : 1;
    }

    private int multiplyOrZero(Integer value, int multiplier) {
        return value != null ? value * multiplier : 0;
    }

    private Integer multiplyNullable(Integer value, int multiplier) {
        return value != null ? value * multiplier : null;
    }
}
