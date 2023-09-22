package za.co.investorWithdrawal.data.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class ResponseMessageDTO {
    private Integer code;
    private String description;
}
