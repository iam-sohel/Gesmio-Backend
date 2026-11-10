package com.gesmio.platform.products.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.products.entity.ProductTeam;
import com.gesmio.platform.products.repository.ProductTeamRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductTeamService extends CrudService<ProductTeam> {

    public ProductTeamService(ProductTeamRepository repository, AuditService auditService) {
        super(repository, repository, ProductTeam.class, "product_teams", auditService);
    }
}
