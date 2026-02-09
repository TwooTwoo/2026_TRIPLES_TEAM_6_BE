package com.lastcup.api.domain.user.service;

import com.lastcup.api.domain.user.domain.UserNotificationSetting;
import com.lastcup.api.domain.user.dto.response.NotificationSettingResponse;
import com.lastcup.api.domain.user.dto.response.UpdateNotificationSettingResponse;
import com.lastcup.api.domain.user.repository.UserNotificationSettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@Transactional
public class UserNotificationSettingService {

    private static final boolean DEFAULT_ENABLED = true;
    private static final LocalTime DEFAULT_RECORD_REMIND_AT = LocalTime.of(14, 0, 0);
    private static final LocalTime DEFAULT_DAILY_CLOSE_AT = LocalTime.of(21, 0, 0);

    private final UserNotificationSettingRepository repository;

    public UserNotificationSettingService(UserNotificationSettingRepository repository) {
        this.repository = repository;
    }

    public NotificationSettingResponse findOrCreate(Long userId) {
        UserNotificationSetting setting = repository.findById(userId)
                .orElseGet(() -> createDefault(userId));
        return toResponse(setting);
    }

    public UpdateNotificationSettingResponse update(
            Long userId,
            Boolean isEnabled,
            LocalTime recordRemindAt,
            LocalTime dailyCloseAt
    ) {
        UserNotificationSetting setting = repository.findById(userId)
                .orElseGet(() -> createDefault(userId));

        setting.update(isEnabled, recordRemindAt, dailyCloseAt);
        repository.save(setting);

        return new UpdateNotificationSettingResponse(true);
    }

    /**
     * 회원가입 시 기본 알림 설정을 생성합니다.
     * 기획 회의에서 정의한 14:00, 19:00 기본값을 사용합니다.
     * 이미 존재하면 무시합니다.
     */
    public void ensureDefaultExists(Long userId) {
        if (repository.existsById(userId)) {
            return;
        }
        createDefault(userId);
    }

    private UserNotificationSetting createDefault(Long userId) {
        UserNotificationSetting created = UserNotificationSetting.createDefault(
                userId,
                DEFAULT_ENABLED,
                DEFAULT_RECORD_REMIND_AT,
                DEFAULT_DAILY_CLOSE_AT
        );
        return repository.save(created);
    }

    private NotificationSettingResponse toResponse(UserNotificationSetting setting) {
        return new NotificationSettingResponse(
                setting.isEnabled(),
                setting.getRecordRemindAt(),
                setting.getDailyCloseAt(),
                setting.getCreatedAt(),
                setting.getUpdatedAt()
        );
    }
}
