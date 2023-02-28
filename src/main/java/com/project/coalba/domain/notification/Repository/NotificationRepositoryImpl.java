package com.project.coalba.domain.notification.Repository;

import com.project.coalba.domain.notification.entity.Notification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.project.coalba.domain.notification.entity.QNotification.*;
import static com.project.coalba.domain.profile.entity.QBoss.boss;
import static com.project.coalba.domain.profile.entity.QStaff.staff;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Notification> getNotificationByStaff(Long staffId) {
        Notification noti = queryFactory.selectFrom(notification)
                .join(staff)
                .on(staff.user.id.eq(notification.user.id))
                .where(staff.id.eq(staffId))
                .fetchOne();

        return Optional.ofNullable(noti);
    }

    @Override
    public Optional<Notification> getNotificationByBoss(Long bossId) {
        Notification noti = queryFactory.selectFrom(notification)
                .join(boss)
                .on(boss.user.id.eq(notification.user.id))
                .where(boss.id.eq(bossId))
                .fetchOne();

        return Optional.ofNullable(noti);
    }
}
