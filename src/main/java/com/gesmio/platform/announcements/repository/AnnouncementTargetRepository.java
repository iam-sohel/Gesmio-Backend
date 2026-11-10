package com.gesmio.platform.announcements.repository;

import com.gesmio.platform.announcements.entity.AnnouncementTarget;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnnouncementTargetRepository extends JpaRepository<AnnouncementTarget, UUID>, JpaSpecificationExecutor<AnnouncementTarget> {
}
