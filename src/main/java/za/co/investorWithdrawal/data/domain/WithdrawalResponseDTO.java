package za.co.investorWithdrawal.data.domain;

import io.micrometer.core.lang.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import za.co.investorWithdrawal.data.WithdrawalResponse;

@Getter
@Setter
@Builder
public class WithdrawalResponseDTO {
    @Nullable
    private WithdrawalResponse withdrawn;
    private ResponseMessageDTO response;
}
