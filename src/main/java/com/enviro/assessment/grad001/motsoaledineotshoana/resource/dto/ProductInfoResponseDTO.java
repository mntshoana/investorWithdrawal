package com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto;

import com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap.ProductInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import org.springframework.lang.Nullable;

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
