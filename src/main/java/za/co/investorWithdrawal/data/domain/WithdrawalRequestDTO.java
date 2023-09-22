package za.co.investorWithdrawal.data.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalRequestDTO {
    private String userId;
    private Integer prodId;
    private String amount;
}
