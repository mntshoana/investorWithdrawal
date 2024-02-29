package com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPersonalInfo {
    @Schema(example="John")
    private String name;
    @Schema(example="John")
    private String surname;
    @Schema(example="1984-10-31", pattern = "yyyy-MM-dd")
    private String dateOfBirth;
}
