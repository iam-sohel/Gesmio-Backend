package com.gesmio.platform.support.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.support.entity.SupportCategory;
import com.gesmio.platform.support.repository.SupportCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class SupportCategoryService extends CrudService<SupportCategory> {

    public SupportCategoryService(SupportCategoryRepository repository, AuditService auditService) {
        super(repository, repository, SupportCategory.class, "support_categories", auditService);
    }
}
