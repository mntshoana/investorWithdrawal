package za.co.investorWithdrawal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.investorWithdrawal.data.ProductInfo;

import za.co.investorWithdrawal.data.domain.ProductInfoResponseDTO;
import za.co.investorWithdrawal.service.repository.UserAccountRepositoryService;

import java.util.List;

@Service
public class ProductsService {
    @Autowired
    UserAccountRepositoryService userAccountRepositoryService;


    public ResponseEntity getProductList(Long userId) {
        try {
            List<ProductInfo> productInfoList = userAccountRepositoryService.getUserProduct(userId);
            if (productInfoList != null)
                return new ResponseEntity<>(ProductInfoResponseDTO.builder()
                    .productList(productInfoList)
                    .response(ResponseUtils.successResponse())
                    .build(), HttpStatus.OK);
            else {
                return new ResponseEntity<>(ProductInfoResponseDTO.builder()
                        .error(ResponseUtils.notFoundError("Error! Unable to find user with provided id"))
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ProductInfoResponseDTO.builder()
                    .response(ResponseUtils.systemError())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
