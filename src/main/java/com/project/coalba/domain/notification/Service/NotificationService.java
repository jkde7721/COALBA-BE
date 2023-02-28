package com.project.coalba.domain.notification.Service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.notification.Repository.NotificationRepository;
import com.project.coalba.domain.notification.entity.Notification;
import com.project.coalba.global.exception.BusinessException;
import com.project.coalba.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.coalba.global.exception.ErrorCode.NOTIFICATION_NOT_FOUNE;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final UserUtil userUtil;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void save(String deviceToken) {
        User currentUser = userUtil.getCurrentUser();
        Optional<Notification> notification = notificationRepository.findByUser(currentUser);
        notification.ifPresentOrElse(
                noti -> noti.updateDeviceToken(deviceToken),
                () -> notificationRepository.save(Notification.builder().user(currentUser).deviceToken(deviceToken).build()));
    }

    @Transactional
    public String getDeviceTokenByStaff(Long staffId) {
        Optional<Notification> notificationByStaff = notificationRepository.getNotificationByStaff(staffId);
        notificationByStaff.orElseThrow(() -> new BusinessException(NOTIFICATION_NOT_FOUNE));
        return notificationByStaff.get().getDeviceToken();
    }

    @Transactional
    public String getDeviceTokenByBoss(Long bossId) {
        Optional<Notification> notificationByBoss = notificationRepository.getNotificationByBoss(bossId);
        notificationByBoss.orElseThrow(() -> new BusinessException(NOTIFICATION_NOT_FOUNE));
        return notificationByBoss.get().getDeviceToken();
    }
}
