package com.gesmio.platform.attendance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public final class AttendanceDtos {

    private AttendanceDtos() {
    }

    /** workMode e.g. OFFICE, REMOTE, HYBRID (optional, defaults to OFFICE) */
    public record CheckInRequest(String workMode) {
    }

    public record AdjustmentRequest(@NotNull UUID attendanceRecordId, @NotBlank String reason,
                                    Instant newCheckInAt, Instant newCheckOutAt) {
    }

    /** decision must be APPROVED or REJECTED */
    public record AdjustmentDecision(@NotBlank String decision) {
    }
}
