package za.co.investorWithdrawal.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import za.co.investorWithdrawal.data.ProductInfo;
import za.co.investorWithdrawal.data.WithdrawalResult;
import za.co.investorWithdrawal.data.domain.WithdrawalRequestDTO;
import za.co.investorWithdrawal.service.repository.UserAccountRepository;
import za.co.investorWithdrawal.service.repository.UserAccountRepositoryService;
import za.co.investorWithdrawal.service.repository.UserInfoRepository;
import za.co.investorWithdrawal.service.repository.entity.UserAccountEntity;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;
import static za.co.investorWithdrawal.TestUtils.makeUserInfoEntity;
import static za.co.investorWithdrawal.TestUtils.makeWithdrawalRequest;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class WithdrawalServiceTest {
    @InjectMocks
    UserAccountRepositoryService userAccountRepositoryService;

    @Mock
    UserInfoRepository userInfoRepository;

    @Mock
    UserAccountRepository userAccountRepository;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getProductListWhenUserFoundTest() throws Exception {
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


        WithdrawalResult result = userAccountRepositoryService.withdraw(request);

        assert (result != null);

        assert(result.getProdId() != null);
        assert(result.getAmount() != null);
        assert(result.getOpeningBalance() != null);
        assert(result.getClosingBalance() != null);
    }
}
