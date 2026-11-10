package com.gesmio.platform.announcements.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.announcements.entity.AnnouncementTarget;
import com.gesmio.platform.announcements.service.AnnouncementTargetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/announcements/announcement-targets")
public class AnnouncementTargetController extends CrudController<AnnouncementTarget> {

    public AnnouncementTargetController(AnnouncementTargetService service) {
        super(service);
    }
}
