package za.co.investorWithdrawal.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import za.co.investorWithdrawal.constants.ProductType;
import za.co.investorWithdrawal.constants.WithdrawalState;
import za.co.investorWithdrawal.data.WithdrawalResult;
import za.co.investorWithdrawal.data.domain.WithdrawalRequestDTO;
import za.co.investorWithdrawal.service.Scheduler;
import za.co.investorWithdrawal.service.Utils;
import za.co.investorWithdrawal.service.repository.entity.UserAccountEntity;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;
import za.co.investorWithdrawal.service.repository.entity.WithdrawalEntity;
import za.co.investorWithdrawal.service.repository.entity.WithdrawalStatusTypeEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalRepositoryService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Autowired
    private WithdrawalStatusTypeRepository withdrawalStatusTypeRepository;


    @Transactional(readOnly = true)
    public WithdrawalResult withdraw(WithdrawalRequestDTO request) {
        Long userId = request.getUserId();
        Optional<UserInfoEntity> possibleUser = userInfoRepository.findById(userId);
        if (possibleUser.isEmpty()) {
            return null;
        }

        Long prodId = request.getProdId();
        Optional<UserAccountEntity> possibleAccount = userAccountRepository.findBy(userId, prodId);
        if (possibleAccount.isEmpty()) {
            return makeWithdrawalResultWithError(prodId,
                    "Error! Unable to find an associated account with the provided userId and prodId");
        }

        UserAccountEntity account = possibleAccount.get();

        // Validate
        String errorMessage = fullValidation(request.getAmount(), account);
        if (errorMessage != null) {
            return makeWithdrawalResultWithError(prodId, errorMessage);
        }

        BigDecimal amount = Utils.fromStringToBigDecimal(request.getAmount());
        String randAmount = Utils.fromBigDecimalToRands(amount);
        try {
            return scheduleWithdrawal(userId, prodId, amount, account);
        } catch (Exception e) {
            e.printStackTrace();
            return makeWithdrawalResultWithError(prodId, randAmount,
                    "Unable to processing your request. Please try again later.");
        }

    }


    // -----------------------------------UTILS------------------------------------
    protected WithdrawalResult scheduleWithdrawal(Long userId, Long prodId,
                                                  final BigDecimal amount,
                                                  UserAccountEntity account) {
        BigDecimal openingBalance = account.getBalance();
        BigDecimal closingBalance = openingBalance.subtract(amount);

        Scheduler.schedule(userId, amount, prodId);

        String randAmount = Utils.fromBigDecimalToRands(amount);
        return WithdrawalResult.builder()
                .isSuccessful(true)
                .prodId(prodId)
                .amount(randAmount)
                .openingBalance(Utils.fromBigDecimalToRands(openingBalance))
                .closingBalance(Utils.fromBigDecimalToRands(closingBalance))
                .build();
    }

    @Modifying(clearAutomatically = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private WithdrawalEntity initiateWithdrawalEntityWithStatusStarted(BigDecimal amount, UserAccountEntity account) {
        WithdrawalEntity transaction = new WithdrawalEntity();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setPreviousBalance(account.getBalance());
        Optional<WithdrawalStatusTypeEntity> type = withdrawalStatusTypeRepository.findById(WithdrawalState.STARTED.ordinal());
        if (!type.isPresent())
            throw new RuntimeException("Withdrawal type STARTED cannot be found!");
        transaction.setStatus(type.get());
        return transaction;
    }

    @Modifying(clearAutomatically = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onStart(Long userId, Long prodId, BigDecimal amount) {
        Optional<UserAccountEntity> possibleAccount = userAccountRepository.findBy(userId,prodId);

        if (possibleAccount.isEmpty())
            throw new RuntimeException("Error! Unable to find account");

        UserAccountEntity account = possibleAccount.get();
        WithdrawalEntity transaction = initiateWithdrawalEntityWithStatusStarted(amount, account);
        withdrawalRepository.saveAndFlush(transaction);
    }

    @Modifying(clearAutomatically = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onExecute() {
        System.out.println("Herere");

        List<WithdrawalEntity> transactionList = withdrawalRepository.findAllByStatusStatus(WithdrawalState.STARTED.ordinal());

        if (transactionList.size() == 0)
            return;

        Optional<WithdrawalStatusTypeEntity> type = withdrawalStatusTypeRepository.findById(WithdrawalState.EXECUTING.ordinal());
        if (type.isEmpty())
            throw new RuntimeException("Withdrawal type EXECUTING cannot be found!");

        for (WithdrawalEntity transaction : transactionList) {
            BigDecimal openingBalance = transaction.getAccount().getBalance();
            BigDecimal closingBalance = openingBalance.subtract(transaction.getAmount());
            String amount = Utils.fromBigDecimalToCurrency(transaction.getAmount());
            if (fullValidation(amount, transaction.getAccount()) == null)
                transaction.getAccount().setBalance(closingBalance);
            transaction.setStatus(type.get());
        }

        withdrawalRepository.saveAllAndFlush(transactionList);
    }

    @Modifying(clearAutomatically = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onDone() {
        List<WithdrawalEntity> transactionList = withdrawalRepository.findAllByStatusStatus(WithdrawalState.STARTED.ordinal());
        if (transactionList.size() == 0)
            return;

        Optional<WithdrawalStatusTypeEntity> typeSuccess = withdrawalStatusTypeRepository.findById(WithdrawalState.DONE.ordinal());
        Optional<WithdrawalStatusTypeEntity> typeFailed = withdrawalStatusTypeRepository.findById(WithdrawalState.DONE.ordinal());
        if (typeSuccess.isEmpty() || typeFailed.isEmpty())
            throw new RuntimeException("Withdrawal type DONE/FAILED cannot be found!");

        for (WithdrawalEntity transaction : transactionList) {
            BigDecimal previousBalance = transaction.getPreviousBalance();
            BigDecimal closingBalance = transaction.getAccount().getBalance();
            if (previousBalance.equals(closingBalance))
                transaction.setStatus(typeFailed.get());
            else
                transaction.setStatus(typeSuccess.get());
        }
        withdrawalRepository.saveAllAndFlush(transactionList);
    }


    // ------------------------WITHDRAWAL VALIDATION UTILS-------------------------------

    private BigDecimal validateAmount(String amountString) throws IllegalArgumentException {
        final BigDecimal amount = Utils.fromStringToBigDecimal(amountString);
        if (amount.compareTo(BigDecimal.ONE) < 0)
            throw new IllegalArgumentException("");
        return amount;
    }

    private String fullValidation(String stringAmount,
                                  UserAccountEntity account) {
        try {
            BigDecimal amount = validateAmount(stringAmount);
            BigDecimal openingBalance = account.getBalance();

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
            long customerAge = Utils.getAge(Utils.localToDate(account.getUser().getDateOfBirth()));
            if (userProduct.equals(typeRetirement) && customerAge <= 65)
                return "Error! You may only withdraw from a retirement type account if you're 66 years or older!";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error! You have provided an invalid amount. Format required x,xxx,xxx.xx";
        }

        return null;
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
}
