package com.gesmio.platform.products.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.products.entity.ProductTeam;
import com.gesmio.platform.products.service.ProductTeamService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products/product-teams")
public class ProductTeamController extends CrudController<ProductTeam> {

    public ProductTeamController(ProductTeamService service) {
        super(service);
    }
}
