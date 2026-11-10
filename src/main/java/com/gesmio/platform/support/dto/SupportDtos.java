package com.gesmio.platform.support.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public final class SupportDtos {

    private SupportDtos() {
    }

    public record CreateTicketRequest(UUID categoryId, @NotBlank String subject, String description, String priority) {
    }

    public record CommentRequest(@NotBlank String comment) {
    }
}
