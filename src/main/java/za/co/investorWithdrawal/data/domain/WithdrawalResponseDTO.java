package za.co.investorWithdrawal.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import za.co.investorWithdrawal.constants.Constants;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WithdrawalResponseDTO {
    @Schema(type = "integer",
            minimum="1", maximum="" + Long.MAX_VALUE,
            example="787890901111", required = true,
            pattern = Constants.ACCOUNT_REGEX

    )
    private Long prodId;
    @Schema(type = "string",
            minimum="1",
            required = true,
            pattern = "R " + Constants.BIG_DECIMAL_REGEX
    )
    private String amount;
    @Nullable
    @Schema(type = "string",
            minimum="1",
            required = true,
            pattern = "R " + Constants.BIG_DECIMAL_REGEX
    )
    private String openingBalance;
    @Nullable
    @Schema(type = "string",
            minimum="1",
            required = true,
            pattern = "R " + Constants.BIG_DECIMAL_REGEX
    )
    private String closingBalance;

    @Nullable
    @Schema(nullable = true)
    private ResponseMessageDTO response;
    @Nullable
    @Schema(nullable = true)
    private ResponseMessageDTO errorMessage;
}
