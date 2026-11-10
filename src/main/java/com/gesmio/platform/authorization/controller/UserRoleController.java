package com.gesmio.platform.authorization.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.authorization.entity.UserRole;
import com.gesmio.platform.authorization.service.UserRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorization/user-roles")
public class UserRoleController extends CrudController<UserRole> {

    public UserRoleController(UserRoleService service) {
        super(service);
    }
}
