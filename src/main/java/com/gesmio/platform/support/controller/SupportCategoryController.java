package com.gesmio.platform.support.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.support.entity.SupportCategory;
import com.gesmio.platform.support.service.SupportCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/support/support-categories")
public class SupportCategoryController extends CrudController<SupportCategory> {

    public SupportCategoryController(SupportCategoryService service) {
        super(service);
    }
}
