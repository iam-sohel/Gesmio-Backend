package com.gesmio.platform.notifications.controller;

import com.gesmio.platform.common.crud.PageResponse;
import com.gesmio.platform.common.exception.ResourceNotFoundException;
import com.gesmio.platform.common.security.SecurityUtils;
import com.gesmio.platform.notifications.entity.Notification;
import com.gesmio.platform.notifications.repository.NotificationRepository;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me/notifications")
@Transactional
public class MeNotificationController {

    private final NotificationRepository repository;

    public MeNotificationController(NotificationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public PageResponse<Notification> mine(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return PageResponse.of(repository.findByRecipientUserId(SecurityUtils.currentUserId(), pageable));
    }

    @GetMapping("/unread-count")
    @Transactional(readOnly = true)
    public Map<String, Long> unreadCount() {
        return Map.of("unread", repository.countByRecipientUserIdAndReadAtIsNull(SecurityUtils.currentUserId()));
    }

    @PostMapping("/{id}/read")
    public Notification markRead(@PathVariable UUID id) {
        Notification n = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        if (!n.getRecipientUserId().equals(SecurityUtils.currentUserId())) {
            throw new ResourceNotFoundException("Notification not found");
        }
        if (n.getReadAt() == null) {
            n.setReadAt(Instant.now());
        }
        return n;
    }

    @PostMapping("/read-all")
    public Map<String, Integer> markAllRead() {
        return Map.of("updated", repository.markAllRead(SecurityUtils.currentUserId(), Instant.now()));
    }
}
