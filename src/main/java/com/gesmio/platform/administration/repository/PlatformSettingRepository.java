package com.gesmio.platform.administration.repository;

import com.gesmio.platform.administration.entity.PlatformSetting;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlatformSettingRepository extends JpaRepository<PlatformSetting, UUID>, JpaSpecificationExecutor<PlatformSetting> {
}
