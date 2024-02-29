package com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto;

import com.enviro.assessment.grad001.motsoaledineotshoana.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalRequestDTO {

    @Schema(type = "integer",
            minimum="1", maximum="" + Long.MAX_VALUE,
            example="2", required = true,
            pattern = Constants.NUMBER_REGEX

    )
    private Long userId;
    @Schema(type = "integer",
            minimum="1", maximum="" + Long.MAX_VALUE,
            example="787890901111", required = true,
            pattern = Constants.ACCOUNT_REGEX
    )
    private Long prodId;
    @Schema(type = "string",
            minimum="1",
            required = true,
            pattern = Constants.BIG_DECIMAL_REGEX
    )
    private String amount;
}
