package za.co.investorWithdrawal.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import za.co.investorWithdrawal.service.ProductsService;
import za.co.investorWithdrawal.service.UserService;
import za.co.investorWithdrawal.service.WithdrawalService;

import za.co.investorWithdrawal.data.domain.WithdrawalRequestDTO;


@RestController
public class Controller {

    @Autowired
    UserService userService;

    @Autowired
    ProductsService productsService;

    @Autowired
    WithdrawalService withdrawalService;

    @GetMapping(value = "api/v1/info",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInvestorInformation(@RequestBody String userId) {
        return userService.getInfo(userId);
    }

    @GetMapping(value = "api/v1/product/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProductsInvestorHasInvestedIn(@RequestBody String userId) {
        return productsService.getProductList(userId);
    }

    @PostMapping(value = "api/v1/product/withdraw",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity withdraw(@RequestBody WithdrawalRequestDTO request) {
        return withdrawalService.withdraw(request);
    }
}
