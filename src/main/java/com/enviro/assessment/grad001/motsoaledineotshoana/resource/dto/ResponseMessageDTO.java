package com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResponseMessageDTO {
    @Schema(
            type = "integer",
            required = true
    )
    private Integer code;
    @Schema(
            type = "string",
            required = true
    )
    private String description;

    @Schema(
            type = "timestamp",
            required = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime timestamp;
}


