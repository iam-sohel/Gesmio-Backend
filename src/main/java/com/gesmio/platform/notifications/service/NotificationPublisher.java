package com.gesmio.platform.notifications.service;

import com.gesmio.platform.notifications.entity.Notification;
import com.gesmio.platform.notifications.repository.NotificationRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Creates in-app notifications for other modules (leave, attendance, ...). */
@Service
public class NotificationPublisher {

    private final NotificationRepository repository;

    public NotificationPublisher(NotificationRepository repository) {
        this.repository = repository;
    }

    /** Does nothing when recipientUserId is null. */
    @Transactional
    public void notify(UUID recipientUserId, String type, String title, String message,
                       String referenceType, UUID referenceId) {
        if (recipientUserId == null) {
            return;
        }
        Notification n = new Notification();
        n.setRecipientUserId(recipientUserId);
        n.setNotificationType(type);
        n.setTitle(title);
        n.setMessage(message);
        n.setReferenceType(referenceType);
        n.setReferenceId(referenceId);
        repository.save(n);
    }
}
