package za.co.investorWithdrawal.data.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import org.springframework.lang.Nullable;
import za.co.investorWithdrawal.data.UserInfo;

@Getter
@Setter
@Builder
public class UserInfoResponseDTO {
    @Nullable
    private UserInfo user;
    private ResponseMessageDTO response;
}
