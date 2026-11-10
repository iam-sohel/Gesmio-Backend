package com.gesmio.platform.administration.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.administration.entity.PlatformSetting;
import com.gesmio.platform.administration.service.PlatformSettingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/administration/platform-settings")
public class PlatformSettingController extends CrudController<PlatformSetting> {

    public PlatformSettingController(PlatformSettingService service) {
        super(service);
    }
}
