package com.gesmio.platform.support.repository;

import com.gesmio.platform.support.entity.SupportTicket;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, UUID>, JpaSpecificationExecutor<SupportTicket> {

    Page<SupportTicket> findByRequesterEmployeeId(UUID employeeId, Pageable pageable);
}
