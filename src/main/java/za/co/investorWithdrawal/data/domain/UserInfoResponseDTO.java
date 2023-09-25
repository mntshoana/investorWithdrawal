package za.co.investorWithdrawal.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import org.springframework.lang.Nullable;
import za.co.investorWithdrawal.data.UserInfo;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoResponseDTO {
    @Nullable
    private UserInfo user;
    @Nullable
    private ResponseMessageDTO response;
    @Nullable
    private ResponseMessageDTO error;

}
