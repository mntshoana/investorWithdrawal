package za.co.investorWithdrawal.service.repository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import za.co.investorWithdrawal.data.ProductInfo;
import za.co.investorWithdrawal.data.ProductType;
import za.co.investorWithdrawal.data.WithdrawalResult;
import za.co.investorWithdrawal.data.domain.WithdrawalRequestDTO;
import za.co.investorWithdrawal.service.Utils;
import za.co.investorWithdrawal.service.repository.entity.UserAccountEntity;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountRepositoryService {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    public List<ProductInfo> getUserProduct(Long userId) {
        Optional<UserInfoEntity> possibleUser = userInfoRepository.findById(userId);
        if (possibleUser.isPresent()) {
            List<UserAccountEntity> accountList = userAccountRepository
                    .findAllByUserId(possibleUser.get().getId());

            return makeProductInfoList(accountList);
        } else {
            return null;
        }
    }
    @Modifying(clearAutomatically = true)
    public WithdrawalResult withdraw(WithdrawalRequestDTO request) {
        Optional<UserInfoEntity> possibleUser = userInfoRepository.findById(request.getUserId());

        // Validate request values
        Long prodId = request.getProdId();
        BigDecimal amount = null;
        String randAmount = null;
        try {
            amount = validateAmount(request.getAmount());
            randAmount = Utils.fromBigDecimalToRands(amount);
        } catch (Exception e) {
            e.printStackTrace();
            return makeWithdrawalResultWithError(prodId,
                    "Error! You have provided an invalid amount. Format required x,xxx,xxx.xx");
        }

        if (possibleUser.isPresent()) {
            Optional<UserAccountEntity> possibleAccount = userAccountRepository.findById(prodId);

            if (possibleAccount.isPresent()) {
                // Valid withdrawal here
                UserAccountEntity account = possibleAccount.get();

                BigDecimal openingBalance = account.getBalance();

                String errorMessage = fullValidation(amount, openingBalance, account);
                if (errorMessage != null)
                    return makeWithdrawalResultWithError(prodId, randAmount, errorMessage);

                try {
                    return actionWithdrawal(prodId, amount, openingBalance, account);
                } catch (Exception e) {
                    e.printStackTrace();
                    return makeWithdrawalResultWithError(prodId, randAmount,
                            "Unable to processing your request. Please try again later.");
                }
            } else return  //Account not found
                    makeWithdrawalResultWithError(prodId, randAmount,
                            "Error! Unable to find an associated account with the provided prodId");
        }
        // User not found
        return null;
    }


    // -----------------------------------UTILS------------------------------------
    // -------------------------------PRODUCT UTILS------------------------------------

    private ProductInfo makeProductInfo(UserAccountEntity account) {
        return ProductInfo.builder()
                .prodId(account.getAccountNumber())
                .type(ProductType.of(account.getProductType().getType()))
                .name(account.getProductType().getName())
                .balance(Utils.fromBigDecimalToRands(account.getBalance()))
                .build();
    }

    private List<ProductInfo> makeProductInfoList(List<UserAccountEntity> accountList) {
        List<ProductInfo> productInfoList = new LinkedList<>();
        for (UserAccountEntity account : accountList) {
            ProductInfo product = makeProductInfo(account);
            productInfoList.add(product);
        }
        return productInfoList;
    }


    // ------------------------------WITHDRAWAL UTILS------------------------------------

    private WithdrawalResult actionWithdrawal(Long prodId,
                                              final BigDecimal amount,
                                              BigDecimal openingBalance,
                                              UserAccountEntity account
    ) {
        // Persist withdrawal
        BigDecimal closingBalance = openingBalance.add(BigDecimal.ZERO); // safe copy
        account.setBalance(openingBalance.subtract(amount));
        account = userAccountRepository.saveAndFlush(account);
        String randAmount = Utils.fromBigDecimalToRands(amount);

        WithdrawalResult.WithdrawalResultBuilder result = WithdrawalResult.builder()
                .prodId(prodId)
                .amount(randAmount)
                .openingBalance(Utils.fromBigDecimalToRands(openingBalance));

        if (account != null) {
            closingBalance = account.getBalance();
            return result
                    .isSuccessful(true)
                    .closingBalance(Utils.fromBigDecimalToRands(closingBalance))
                    .build();
        } else return result
                .isSuccessful(false)
                .closingBalance(Utils.fromBigDecimalToRands(closingBalance))
                .errorMessage("There was an error in processing your request. Please check your balance and wait a moment before trying again.")
                .build();

    }


    public WithdrawalResult makeWithdrawalResultWithError(Long prodId, String errorMessage) {
        return WithdrawalResult.builder()
                .isSuccessful(false)
                .prodId(prodId)
                .errorMessage(errorMessage)
                .build();
    }

    public WithdrawalResult makeWithdrawalResultWithError(Long prodId, String randAmount, String errorMessage) {
        return WithdrawalResult.builder()
                .isSuccessful(false)
                .prodId(prodId)
                .amount(randAmount)
                .errorMessage(errorMessage)
                .build();
    }

    // ------------------------WITHDRAWAL VALIDATION UTILS-------------------------------

    private BigDecimal validateAmount(String amountString) throws IllegalArgumentException {
        final BigDecimal amount = Utils.fromStringToBigDecimal(amountString);
        if (amount.compareTo(BigDecimal.ONE) < 0)
            throw new IllegalArgumentException("");
        return amount;
    }

    private String fullValidation(final BigDecimal amount,
                                  BigDecimal openingBalance,
                                  UserAccountEntity account) {
        // amount >= balance
        if (amount.compareTo(openingBalance) >= 0)
            return "Error! Amount cannot be greater than available balance";

        // amount > 90% of the balance
        BigDecimal ninetyPercent = openingBalance.multiply(BigDecimal.valueOf(0.9));
        if (amount.compareTo(ninetyPercent) > 0)
            return "Error! Investors cannot withdraw an amount greater than 90% of their balance";

        // retirement account, age <= 65
        String userProduct = account.getProductType().getType();
        String typeRetirement = ProductType.RETIREMENT.toString();
        int customerAge = Utils.getAge(account.getUser().getDateOfBirth());
        if (userProduct.equals(typeRetirement) && customerAge <= 65)
            return "Error! You may only withdraw from a retirement type account if you're 66 years or older!";

        return null;
    }

}
