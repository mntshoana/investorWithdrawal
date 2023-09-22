package za.co.investorWithdrawal.data;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserInfo {
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String email;
    private String cell;
}
