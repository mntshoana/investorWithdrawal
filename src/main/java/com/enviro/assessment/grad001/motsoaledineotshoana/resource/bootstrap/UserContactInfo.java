package com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserContactInfo {
    @Schema(example="email@example.com")
    private String email;
    @Schema(example="0432109876")
    private String cell;
}
