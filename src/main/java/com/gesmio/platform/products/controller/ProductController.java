package com.gesmio.platform.products.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.products.entity.Product;
import com.gesmio.platform.products.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products/products")
public class ProductController extends CrudController<Product> {

    public ProductController(ProductService service) {
        super(service);
    }
}
