package com.gesmio.platform.recruitment.repository;

import com.gesmio.platform.recruitment.entity.JobApplication;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID>, JpaSpecificationExecutor<JobApplication> {
}
