package com.gesmio.platform.authorization.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.authorization.entity.RolePermission;
import com.gesmio.platform.authorization.service.RolePermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorization/role-permissions")
public class RolePermissionController extends CrudController<RolePermission> {

    public RolePermissionController(RolePermissionService service) {
        super(service);
    }
}
