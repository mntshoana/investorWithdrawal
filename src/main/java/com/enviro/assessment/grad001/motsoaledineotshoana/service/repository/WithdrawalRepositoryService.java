package com.enviro.assessment.grad001.motsoaledineotshoana.service.repository;

import com.enviro.assessment.grad001.motsoaledineotshoana.constants.ProductType;
import com.enviro.assessment.grad001.motsoaledineotshoana.constants.WithdrawalState;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap.WithdrawalResult;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.Scheduler;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.Utils;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.UserInfoEntity;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.WithdrawalStatusTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.WithdrawalRequestDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.UserAccountEntity;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.WithdrawalEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    public byte[] listWithdrawals(Long userId, Long prodId) {
        List<WithdrawalEntity> withdrawalList = withdrawalRepository.findAllByUserProduct(userId, prodId);
        if (withdrawalList.size() == 0) {
            return null;
        }

        return buildCSV(withdrawalList);
    }

    public byte[] listWithdrawals(Long userId, Long prodId, Date dateFrom) {
        List<WithdrawalEntity> withdrawalList = withdrawalRepository.findAllByUserProduct(userId, prodId, dateToLocalDateTime(dateFrom));
        if (withdrawalList.size() == 0) {
            return null;
        }

        return buildCSV(withdrawalList);
    }
    public byte[] listWithdrawals(Long userId, Long prodId, Date dateFrom, Date dateTo) {
        List<WithdrawalEntity> withdrawalList = withdrawalRepository.findAllByUserProduct(userId, prodId, dateToLocalDateTime(dateFrom), dateToLocalDateTime(dateTo));
        if (withdrawalList.size() == 0) {
            return null;
        }

        return buildCSV(withdrawalList);
    }

    // -----------------------------------UTILS------------------------------------

    protected LocalDateTime dateToLocalDateTime(Date date){
        return date.toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
    }
    protected byte[] buildCSV(List<WithdrawalEntity> withdrawalList){
        final String HEADER_LINE = "Date,Amount,Previous Balance,Balance\n";

        StringBuilder csv = new StringBuilder();
        csv.append(HEADER_LINE);

        for (WithdrawalEntity employee : withdrawalList) {
            csv.append(employee.getModified()).append(",")
                    .append(employee.getAmount()).append(",")
                    .append(employee.getPreviousBalance()).append(",")
                    .append(employee.getPreviousBalance().subtract(employee.getAmount())).append(",")
                    .append("\n");
        }

        return csv.toString().getBytes();
    }

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
        transaction.setUser(account.getUser());
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
