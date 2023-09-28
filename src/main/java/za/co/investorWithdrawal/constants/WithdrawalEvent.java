package za.co.investorWithdrawal.constants;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public enum WithdrawalEvent {
    START, EXECUTE, FINISH;

    @Getter
    @Setter
    private BigDecimal amount;
    @Getter
    @Setter
    private Long prodId;
    @Getter
    @Setter
    private Long withdrawalId;

    @Getter
    @Setter
    private Long userId;

    WithdrawalEvent() {
        this.amount = null;
        this.prodId = null;
        this.userId = null;
    }
}
