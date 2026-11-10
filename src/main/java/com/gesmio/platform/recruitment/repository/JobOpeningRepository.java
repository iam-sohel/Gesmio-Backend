package com.gesmio.platform.recruitment.repository;

import com.gesmio.platform.recruitment.entity.JobOpening;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobOpeningRepository extends JpaRepository<JobOpening, UUID>, JpaSpecificationExecutor<JobOpening> {
}
