package za.co.investorWithdrawal;

import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.investorWithdrawal.data.*;
import za.co.investorWithdrawal.data.domain.*;
import za.co.investorWithdrawal.service.Utils;
import za.co.investorWithdrawal.service.repository.entity.ProductTypeEntity;
import za.co.investorWithdrawal.service.repository.entity.UserAccountEntity;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TestUtils {
    public static WithdrawalRequestDTO makeWithdrawalRequest() {
        WithdrawalRequestDTO request = new WithdrawalRequestDTO();
        request.setUserId(2L);
        request.setProdId(787890901111L);
        request.setAmount("82.0");
        return request;
    }

    public static ResponseEntity<UserInfoResponseDTO> makeUserInfoResponse() {
        UserPersonalInfo personalInfo = UserPersonalInfo.builder()
                .name("Sarah Janoury")
                .surname("Lesuka")
                .dateOfBirth("1980-00-21")
                .build();
        UserContactInfo contact = UserContactInfo.builder()
                .email("sarah.@address.com")
                .cell("0122109869")
                .build();

        UserInfo user = UserInfo.builder()
                .personal(personalInfo)
                .address("123 Somewhere Drive,\nBlock 5,\nOld Town,\nSouthernland,\n0093\n")
                .contact(contact)
                .build();
        ResponseMessageDTO response = makeSuccessResponse();

        UserInfoResponseDTO object = UserInfoResponseDTO.builder()
                .user(user)
                .response(response)
                .build();

        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    public static ResponseMessageDTO makeSuccessResponse() {
        return ResponseMessageDTO.builder()
                .code(200)
                .description("Success")
                .build();
    }

    public static ResponseEntity<ProductInfoResponseDTO> makeProductInfoResponse() {
        ProductInfo product = ProductInfo.builder()
                .prodId(787890901111L)
                .type(ProductType.RETIREMENT)
                .name("John Wick")
                .balance("R 4.0")
                .build();
        ResponseMessageDTO response = makeSuccessResponse();

        List productList = new LinkedList<ProductInfo>();

        productList.add(product);
        return new ResponseEntity(ProductInfoResponseDTO.builder()
                .productList(productList)
                .response(response)
                .build(), HttpStatus.OK);
    }

    public static ResponseEntity<WithdrawalResponseDTO> makeWithdrawalResponse(WithdrawalRequestDTO request) {
        BigDecimal openningBalance = new BigDecimal("965985");
        BigDecimal closingbalance = openningBalance.subtract(Utils.fromStringToBigDecimal(request.getAmount()));
        return new ResponseEntity(WithdrawalResponseDTO.builder()
                .prodId(787890901111L)
                .amount("R " + request.getAmount())
                .openingBalance(Utils.fromBigDecimalToRands(openningBalance))
                .closingBalance(Utils.fromBigDecimalToRands(closingbalance))
                .response(makeSuccessResponse())
                .build(),
                HttpStatus.OK);
    }

    public static UserInfoEntity makeUserInfoEntity(){
        UserInfoEntity entity = new UserInfoEntity();
        entity.setId(5L);
        entity.setFirstName("Thabo");
        entity.setMiddleName("Thube");
        entity.setLastName("Lebopo");
        entity.setDateOfBirth(LocalDate.of(1994,05,06));
        entity.setCell("0123456789");
        entity.setEmail("test@email.com");
        entity.setAddressLine1("2nd Street");
        entity.setAddressLine2("Unit 1");
        entity.setCity("Johannesburg");
        entity.setCountry("South Africa");
        entity.setZipCode("1234");

        List<UserAccountEntity> accounts =  new LinkedList<>();
        accounts.add(makeUserAccount( 9876543210L, entity));
        entity.setAccountList(accounts);

        return entity;
    }

    public static UserAccountEntity makeUserAccount(Long accNumber, UserInfoEntity user){
        UserAccountEntity entity = new UserAccountEntity();
       entity.setAccountNumber(accNumber);
       entity.setProductType(makeProductTypeEntity());
       entity.setUser(user);
       entity.setBalance(new BigDecimal("36000"));
       return entity;
    }

    public static ProductTypeEntity makeProductTypeEntity(){
        ProductTypeEntity entity = new ProductTypeEntity();
        entity.setProdId(1);
        entity.setType(ProductType.SAVINGS.toString());
        entity.setName("Test savings account");
        return entity;
    }
}
