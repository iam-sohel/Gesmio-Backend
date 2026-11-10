package com.gesmio.platform.products.service;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.CrudService;
import com.gesmio.platform.products.entity.Product;
import com.gesmio.platform.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends CrudService<Product> {

    public ProductService(ProductRepository repository, AuditService auditService) {
        super(repository, repository, Product.class, "products", auditService);
    }
}
