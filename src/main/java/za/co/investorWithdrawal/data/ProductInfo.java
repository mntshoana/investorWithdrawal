package za.co.investorWithdrawal.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import za.co.investorWithdrawal.constants.Constants;

@Getter
@Setter
@Builder
public class ProductInfo {
    @Schema(type = "integer",
            minimum="1", maximum="" + Long.MAX_VALUE,
            example="787890901111", required = true,
            pattern = Constants.ACCOUNT_REGEX

    )
    private Long prodId;
    private ProductType type;
    @Schema(example="John Wick")
    private String name;

    @Schema(type = "string",
            minimum="1",
            required = true,
            pattern = "R " + Constants.BIG_DECIMAL_REGEX
    )
    private String balance;
}
