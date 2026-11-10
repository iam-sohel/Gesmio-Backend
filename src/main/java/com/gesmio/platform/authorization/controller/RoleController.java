package com.gesmio.platform.authorization.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.authorization.entity.Role;
import com.gesmio.platform.authorization.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorization/roles")
public class RoleController extends CrudController<Role> {

    public RoleController(RoleService service) {
        super(service);
    }
}
