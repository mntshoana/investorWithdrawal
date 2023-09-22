package za.co.investorWithdrawal.data;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductInfo {
    private String prodId;
    private ProductType type;
    private String name;
    private BigDecimal balance;
}
