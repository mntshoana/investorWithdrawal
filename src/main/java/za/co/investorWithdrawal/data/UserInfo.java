package za.co.investorWithdrawal.data;

import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class UserInfo {
    private UserPersonalInfo personal;

    @Nullable
    private String address;

    private UserContactInfo contact;
}
