package com.gesmio.platform.support.repository;

import com.gesmio.platform.support.entity.SupportCategory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupportCategoryRepository extends JpaRepository<SupportCategory, UUID>, JpaSpecificationExecutor<SupportCategory> {
}
