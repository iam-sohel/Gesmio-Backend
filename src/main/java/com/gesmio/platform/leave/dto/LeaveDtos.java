package com.gesmio.platform.leave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class LeaveDtos {

    private LeaveDtos() {
    }

    public record ApplyLeaveRequest(@NotNull UUID leavePolicyId, @NotNull LocalDate startDate,
                                    @NotNull LocalDate endDate, BigDecimal requestedDays, String reason) {
    }

    /** decision must be APPROVED or REJECTED */
    public record DecisionRequest(@NotBlank String decision, String comments) {
    }
}
