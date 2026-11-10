package com.gesmio.platform.recruitment.repository;

import com.gesmio.platform.recruitment.entity.CandidateDocument;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CandidateDocumentRepository extends JpaRepository<CandidateDocument, UUID>, JpaSpecificationExecutor<CandidateDocument> {
}
