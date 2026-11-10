package com.gesmio.platform.announcements.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.announcements.entity.AnnouncementTarget;
import com.gesmio.platform.announcements.repository.AnnouncementTargetRepository;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementTargetService extends CrudService<AnnouncementTarget> {

    public AnnouncementTargetService(AnnouncementTargetRepository repository, AuditService auditService) {
        super(repository, repository, AnnouncementTarget.class, "announcement_targets", auditService);
    }
}
