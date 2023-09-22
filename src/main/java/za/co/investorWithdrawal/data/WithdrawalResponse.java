package za.co.investorWithdrawal.data;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class WithdrawalResponse {
    private Integer prodId;
    private BigDecimal amount;
}
