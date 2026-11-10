package com.gesmio.platform.authorization.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.authorization.entity.Permission;
import com.gesmio.platform.authorization.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorization/permissions")
public class PermissionController extends CrudController<Permission> {

    public PermissionController(PermissionService service) {
        super(service);
    }
}
