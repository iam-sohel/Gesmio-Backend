package com.gesmio.platform.common.crud;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class CrudController<T> extends ReadOnlyCrudController<T> {

    protected CrudController(CrudService<T> service) {

        super(service);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public T create(@Valid @RequestBody T body) {
        return service.create(body);
    }

    /**
     * Partial update: fields that are null/absent in the body are left unchanged.
     */
    @PutMapping("/{id}")
    public T update(@PathVariable UUID id, @RequestBody T body) {

        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {

        service.delete(id);
    }
}
