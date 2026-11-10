package com.gesmio.platform.announcements.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.announcements.entity.Announcement;
import com.gesmio.platform.announcements.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService extends CrudService<Announcement> {

    public AnnouncementService(AnnouncementRepository repository, AuditService auditService) {
        super(repository, repository, Announcement.class, "announcements", auditService);
    }
}
