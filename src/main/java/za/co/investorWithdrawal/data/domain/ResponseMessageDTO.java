package za.co.investorWithdrawal.data.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
}


