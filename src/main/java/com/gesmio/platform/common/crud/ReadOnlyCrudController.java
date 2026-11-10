package com.gesmio.platform.common.crud;

import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class ReadOnlyCrudController<T> {

    protected final CrudService<T> service;

    protected ReadOnlyCrudController(CrudService<T> service) {
        this.service = service;
    }

    /** GET ?page=0&size=20&sort=createdAt,desc&status=OPEN  (any entity property works as an equality filter) */
    @GetMapping
    public PageResponse<T> list(@RequestParam Map<String, String> params,
                                @PageableDefault(size = 20) Pageable pageable) {
        return service.list(params, pageable);
    }

    @GetMapping("/{id}")
    public T get(@PathVariable UUID id) {
        return service.get(id);
    }
}
