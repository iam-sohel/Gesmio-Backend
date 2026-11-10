package com.gesmio.platform.support.repository;

import com.gesmio.platform.support.entity.SupportTicketComment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupportTicketCommentRepository extends JpaRepository<SupportTicketComment, UUID>, JpaSpecificationExecutor<SupportTicketComment> {

    List<SupportTicketComment> findByTicketIdOrderByCreatedAtAsc(UUID ticketId);
}
