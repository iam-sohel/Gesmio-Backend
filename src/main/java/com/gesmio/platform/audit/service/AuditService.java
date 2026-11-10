package com.gesmio.platform.audit.service;

import com.gesmio.platform.audit.entity.AuditLog;
import com.gesmio.platform.audit.repository.AuditLogRepository;
import com.gesmio.platform.common.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** Writes audit_logs rows. Actor, IP, user agent and request id are taken from the current request. */
@Service
public class AuditService {

    private final AuditLogRepository repository;

    public AuditService(AuditLogRepository repository) {
        this.repository = repository;
    }

    /** Joins the caller's transaction (rolled back together with the business change). */
    @Transactional(propagation = Propagation.REQUIRED)
    public void record(String action, String entityType, UUID entityId, String outcome) {
        save(action, entityType, entityId, outcome, null);
    }

    /** Own transaction: survives rollback of the caller. Use for failures (e.g. failed logins). */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordIndependent(String action, String entityType, UUID entityId, String outcome, UUID actorOverride) {
        save(action, entityType, entityId, outcome, actorOverride);
    }

    private void save(String action, String entityType, UUID entityId, String outcome, UUID actorOverride) {
        AuditLog log = new AuditLog();
        log.setActorUserId(actorOverride != null ? actorOverride : SecurityUtils.currentUserIdOrNull());
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setOutcome(outcome);
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs) {
            HttpServletRequest req = attrs.getRequest();
            String header = req.getHeader("X-Request-Id");
            log.setRequestId(header != null && header.length() <= 255 ? header : UUID.randomUUID().toString());
            String ua = req.getHeader("User-Agent");
            log.setUserAgent(ua != null && ua.length() > 255 ? ua.substring(0, 255) : ua);
            try {
                log.setIpAddress(InetAddress.getByName(req.getRemoteAddr()));
            } catch (Exception ignored) {
                // leave null
            }
        }
        repository.save(log);
    }
}
