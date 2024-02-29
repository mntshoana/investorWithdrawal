package com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;


@Getter
@Setter
@Builder
public class WithdrawalResult {
    private boolean isSuccessful;
    private Long prodId;
    @Nullable
    private String amount;
    @Nullable
    private String openingBalance;
    @Nullable
    private String closingBalance;
    @Nullable
    private String errorMessage;
}
