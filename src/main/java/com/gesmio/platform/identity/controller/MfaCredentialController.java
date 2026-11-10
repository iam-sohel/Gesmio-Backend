package com.gesmio.platform.identity.controller;

import com.gesmio.platform.common.crud.CrudController;
import com.gesmio.platform.identity.entity.MfaCredential;
import com.gesmio.platform.identity.service.MfaCredentialService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/identity/mfa-credentials")
public class MfaCredentialController extends CrudController<MfaCredential> {

    public MfaCredentialController(MfaCredentialService service) {
        super(service);
    }
}
