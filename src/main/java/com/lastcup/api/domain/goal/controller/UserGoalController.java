package com.lastcup.api.domain.goal.controller;

import com.lastcup.api.domain.goal.domain.UserGoal;
import com.lastcup.api.domain.goal.dto.request.UpdateGoalRequest;
import com.lastcup.api.domain.goal.dto.response.GoalResponse;
import com.lastcup.api.domain.goal.service.UserGoalService;
import com.lastcup.api.global.response.ApiResponse;
import com.lastcup.api.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저(목표 설정) API")
@RestController
@RequestMapping("/api/v1/users/me/goals")
public class UserGoalController {

    private final UserGoalService goalService;

    public UserGoalController(UserGoalService goalService) {
        this.goalService = goalService;
    }

    @Operation(summary = "목표 설정 조회(없으면 자동 생성)", description = "설정이 없으면 기본값으로 생성 후 반환합니다.")
    @SecurityRequirement(name = "BearerAuth")
    @GetMapping
    public ApiResponse<GoalResponse> findGoals(@AuthenticationPrincipal AuthUser authUser) {
        UserGoal goal = goalService.findOrCreateCurrent(authUser.userId());
        return ApiResponse.success(toResponse(goal));
    }

    @Operation(summary = "목표 설정 변경", description = "변경 시 이전 목표는 종료 처리됩니다.")
    @SecurityRequirement(name = "BearerAuth")
    @PatchMapping
    public ApiResponse<GoalResponse> updateGoals(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody @Valid UpdateGoalRequest request
    ) {
        UserGoal goal = goalService.updateGoal(
                authUser.userId(),
                request.dailyCaffeineTarget(),
                request.dailySugarTarget(),
                request.startDate()
        );
        return ApiResponse.success(toResponse(goal));
    }

    private GoalResponse toResponse(UserGoal goal) {
        return new GoalResponse(
                goal.getId(),
                goal.getUserId(),
                goal.getDailyCaffeineTarget(),
                goal.getDailySugarTarget(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getCreatedAt(),
                goal.getUpdatedAt()
        );
    }
}
