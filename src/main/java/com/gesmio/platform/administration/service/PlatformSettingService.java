package com.gesmio.platform.administration.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.administration.entity.PlatformSetting;
import com.gesmio.platform.administration.repository.PlatformSettingRepository;
import org.springframework.stereotype.Service;

@Service
public class PlatformSettingService extends CrudService<PlatformSetting> {

    public PlatformSettingService(PlatformSettingRepository repository, AuditService auditService) {
        super(repository, repository, PlatformSetting.class, "platform_settings", auditService);
    }
}
