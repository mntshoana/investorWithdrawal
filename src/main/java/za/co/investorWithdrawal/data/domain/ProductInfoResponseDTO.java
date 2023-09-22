package za.co.investorWithdrawal.data.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import org.springframework.lang.Nullable;
import za.co.investorWithdrawal.data.ProductInfo;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductInfoResponseDTO {
    @Nullable
    private List<ProductInfo> list;
    private ResponseMessageDTO response;
}
