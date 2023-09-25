package za.co.investorWithdrawal.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import org.springframework.lang.Nullable;
import za.co.investorWithdrawal.data.ProductInfo;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoResponseDTO {
    @Nullable
    private List<ProductInfo> productList;
    @Nullable
    private ResponseMessageDTO response;
    @Nullable
    private ResponseMessageDTO error;
}
