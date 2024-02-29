package com.enviro.assessment.grad001.motsoaledineotshoana.service;

import com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap.WithdrawalResult;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.UserAccountRepository;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.UserAccountRepositoryService;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.UserInfoRepository;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.WithdrawalRepositoryService;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.UserAccountEntity;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.UserInfoEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.WithdrawalRequestDTO;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.enviro.assessment.grad001.motsoaledineotshoana.TestUtils.makeUserInfoEntity;
import static com.enviro.assessment.grad001.motsoaledineotshoana.TestUtils.makeWithdrawalRequest;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class WithdrawalServiceTest {
    @InjectMocks
    UserAccountRepositoryService userAccountRepositoryService;

    @Mock
    UserInfoRepository userInfoRepository;

    @Mock
    UserAccountRepository userAccountRepository;

    @Mock
    WithdrawalRepositoryService withdrawalRepositoryService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void successfulWithdrawalTest() {
        Optional<UserInfoEntity> info = Optional.of(makeUserInfoEntity());
        when(userInfoRepository.findById(any())).thenReturn(info);

        Optional<UserAccountEntity> account = Optional.of(info.get().getAccountList().get(0));
        when(userAccountRepository.findById(any())).thenReturn(account);
        UserAccountEntity accoun2 = makeUserInfoEntity().getAccountList().get(0);

        WithdrawalRequestDTO request = makeWithdrawalRequest();
        BigDecimal closingBalance = accoun2.getBalance().subtract(Utils.fromStringToBigDecimal(request.getAmount()));
        accoun2.setBalance(closingBalance);

        UserAccountEntity account2 = account.get();
        when(userAccountRepository.saveAndFlush(any())).thenReturn(account2);

        when(userAccountRepository.saveAllAndFlush(any())).thenReturn(new LinkedList<>());
        WithdrawalResult result = withdrawalRepositoryService.withdraw(request);

//        assert (result != null);

//        assert(result.getProdId() != null);
//        assert(result.getAmount() != null);
//        assert(result.getOpeningBalance() != null);
//        assert(result.getClosingBalance() != null);
    }
}
