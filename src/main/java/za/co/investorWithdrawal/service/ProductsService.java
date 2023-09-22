package za.co.investorWithdrawal.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.investorWithdrawal.data.ProductInfo;
import za.co.investorWithdrawal.data.ProductType;
import za.co.investorWithdrawal.data.domain.ProductInfoResponseDTO;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductsService {
    public ResponseEntity getProductList(String userId) {
        try {
            // (if user exists)
            ProductInfo example = ProductInfo.builder()
                    .prodId("id")
                    .type(ProductType.RETIREMENT)
                    .name("description")
                    .balance(new BigDecimal(1))
                    .build();
            List<ProductInfo> infoList = new LinkedList<>();
            // for loop
            infoList.add(example);
            // else null
            return new ResponseEntity<>(ProductInfoResponseDTO.builder()
                    .list(infoList)
                    .response(ResponseUtils.successResponse())
                    .build(), HttpStatus.OK);
            // else
            // return an error "Error! Unable to find user with provided id"
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ProductInfoResponseDTO>(ProductInfoResponseDTO.builder()
                    .response(ResponseUtils.systemError())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
