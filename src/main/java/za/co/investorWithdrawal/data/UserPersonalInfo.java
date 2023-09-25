package za.co.investorWithdrawal.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserPersonalInfo {
    @Schema(example="John")
    private String name;
    @Schema(example="John")
    private String surname;
    @Schema(example="1984-10-31", pattern = "yyyy-MM-dd")
    private String dateOfBirth;
}
