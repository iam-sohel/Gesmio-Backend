package com.gesmio.platform.recruitment.repository;

import com.gesmio.platform.recruitment.entity.Interview;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InterviewRepository extends JpaRepository<Interview, UUID>, JpaSpecificationExecutor<Interview> {
}
