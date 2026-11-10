package com.gesmio.platform.common.crud;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.exception.BadRequestException;
import com.gesmio.platform.common.exception.ResourceNotFoundException;
import com.gesmio.platform.common.security.SecurityUtils;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generic CRUD with:
 *  - equality filters on any entity property via query params (?status=OPEN&projectId=...)
 *  - partial updates (null properties in the request body are ignored)
 *  - automatic audit logging and created_by / updated_by population.
 */
@Transactional
public abstract class CrudService<T> {

    private static final Set<String> RESERVED_PARAMS = Set.of("page", "size", "sort");
    private static final Set<String> NEVER_COPIED = Set.of("class", "id", "createdAt");
    private static final List<String> AUTO_USER_PROPS =
            List.of("createdBy", "uploadedBy", "assignedBy", "grantedBy", "requestedBy", "authorUserId", "updatedBy");

    protected final JpaRepository<T, UUID> repository;
    private final JpaSpecificationExecutor<T> specExecutor;
    protected final Class<T> type;
    protected final String entityName;
    protected final AuditService auditService;

    protected CrudService(JpaRepository<T, UUID> repository, JpaSpecificationExecutor<T> specExecutor,
                          Class<T> type, String entityName, AuditService auditService) {
        this.repository = repository;
        this.specExecutor = specExecutor;
        this.type = type;
        this.entityName = entityName;
        this.auditService = auditService;
    }

    @Transactional(readOnly = true)
    public PageResponse<T> list(Map<String, String> filters, Pageable pageable) {
        Specification<T> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, String> e : filters.entrySet()) {
                if (RESERVED_PARAMS.contains(e.getKey())) {
                    continue;
                }
                Path<Object> path;
                try {
                    path = root.get(e.getKey());
                } catch (IllegalArgumentException ex) {
                    throw new BadRequestException("Unknown filter field: " + e.getKey());
                }
                predicates.add(cb.equal(path, convert(path.getJavaType(), e.getKey(), e.getValue())));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<T> page = specExecutor.findAll(spec, pageable);
        return PageResponse.of(page);
    }

    @Transactional(readOnly = true)
    public T get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName + " not found: " + id));
    }

    public T create(T entity) {
        BeanWrapper bw = new BeanWrapperImpl(entity);
        bw.setPropertyValue("id", null);
        UUID currentUser = SecurityUtils.currentUserIdOrNull();
        if (currentUser != null) {
            for (String prop : AUTO_USER_PROPS) {
                if (bw.isWritableProperty(prop) && bw.getPropertyValue(prop) == null) {
                    bw.setPropertyValue(prop, currentUser);
                }
            }
        }
        T saved = repository.save(entity);
        auditService.record("CREATE", entityName, idOf(saved), "SUCCESS");
        return saved;
    }

    public T update(UUID id, T patch) {
        T existing = get(id);
        BeanWrapper src = new BeanWrapperImpl(patch);
        BeanWrapper dst = new BeanWrapperImpl(existing);
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            String name = pd.getName();
            if (NEVER_COPIED.contains(name) || !src.isReadableProperty(name) || !dst.isWritableProperty(name)) {
                continue;
            }
            Object value = src.getPropertyValue(name);
            if (value != null) {
                dst.setPropertyValue(name, value);
            }
        }
        UUID currentUser = SecurityUtils.currentUserIdOrNull();
        if (currentUser != null && dst.isWritableProperty("updatedBy")) {
            dst.setPropertyValue("updatedBy", currentUser);
        }
        T saved = repository.save(existing);
        auditService.record("UPDATE", entityName, id, "SUCCESS");
        return saved;
    }

    public void delete(UUID id) {
        T existing = get(id);
        repository.delete(existing);
        repository.flush(); // surface FK violations inside this request
        auditService.record("DELETE", entityName, id, "SUCCESS");
    }

    private UUID idOf(T entity) {
        return (UUID) new BeanWrapperImpl(entity).getPropertyValue("id");
    }

    private static Object convert(Class<?> target, String field, String raw) {
        try {
            if (target == String.class) return raw;
            if (target == UUID.class) return UUID.fromString(raw);
            if (target == Boolean.class) return Boolean.parseBoolean(raw);
            if (target == Integer.class) return Integer.valueOf(raw);
            if (target == Long.class) return Long.valueOf(raw);
            if (target == BigDecimal.class) return new BigDecimal(raw);
            if (target == LocalDate.class) return LocalDate.parse(raw);
            if (target == Instant.class) return Instant.parse(raw);
        } catch (RuntimeException ex) {
            throw new BadRequestException("Invalid value for filter '" + field + "': " + raw);
        }
        throw new BadRequestException("Filtering by '" + field + "' is not supported");
    }
}
