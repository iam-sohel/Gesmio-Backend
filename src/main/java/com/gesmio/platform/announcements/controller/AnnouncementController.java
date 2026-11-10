package com.gesmio.platform.announcements.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.announcements.entity.Announcement;
import com.gesmio.platform.announcements.service.AnnouncementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/announcements/announcements")
public class AnnouncementController extends CrudController<Announcement> {

    public AnnouncementController(AnnouncementService service) {
        super(service);
    }
}
