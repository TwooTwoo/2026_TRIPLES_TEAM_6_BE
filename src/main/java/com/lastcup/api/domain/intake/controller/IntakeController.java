package com.lastcup.api.domain.intake.controller;

import com.lastcup.api.domain.intake.dto.request.CreateIntakeRequest;
import com.lastcup.api.domain.intake.dto.response.IntakeResponse;
import com.lastcup.api.domain.intake.service.IntakeService;
import com.lastcup.api.global.response.ApiResponse;
import com.lastcup.api.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Intake", description = "섭취 기록 API")
@RestController
@RequestMapping("/api/v1/intakes")
public class IntakeController {

    private final IntakeService intakeService;

    public IntakeController(IntakeService intakeService) {
        this.intakeService = intakeService;
    }

    @Operation(summary = "섭취 기록 생성", description = "음료 섭취를 기록합니다. 영양/목표 스냅샷이 자동 저장됩니다.")
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<IntakeResponse> createIntake(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody @Valid CreateIntakeRequest request
    ) {
        IntakeResponse response = intakeService.createIntake(authUser.userId(), request);
        return ApiResponse.success(response);
    }
}
