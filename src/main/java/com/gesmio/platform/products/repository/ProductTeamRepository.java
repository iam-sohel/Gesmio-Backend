package com.gesmio.platform.products.repository;

import com.gesmio.platform.products.entity.ProductTeam;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductTeamRepository extends JpaRepository<ProductTeam, UUID>, JpaSpecificationExecutor<ProductTeam> {
}
