package com.gesmio.platform.notifications.repository;

import com.gesmio.platform.notifications.entity.Notification;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, UUID>, JpaSpecificationExecutor<Notification> {

    Page<Notification> findByRecipientUserId(UUID userId, Pageable pageable);
    long countByRecipientUserIdAndReadAtIsNull(UUID userId);

    @Modifying
    @Query("update Notification n set n.readAt = :now where n.recipientUserId = :uid and n.readAt is null")
    int markAllRead(@Param("uid") UUID uid, @Param("now") Instant now);
}
