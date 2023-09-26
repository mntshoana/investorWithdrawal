package za.co.investorWithdrawal.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import za.co.investorWithdrawal.data.ProductInfo;
import za.co.investorWithdrawal.service.repository.UserAccountRepository;
import za.co.investorWithdrawal.service.repository.UserAccountRepositoryService;
import za.co.investorWithdrawal.service.repository.UserInfoRepository;
import za.co.investorWithdrawal.service.repository.entity.UserAccountEntity;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static za.co.investorWithdrawal.TestUtils.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

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

        List<UserAccountEntity> accountList = info.get().getAccountList();
        when(userAccountRepository.findAllByUserId(any())).thenReturn(accountList);


        List<ProductInfo> productList =  userAccountRepositoryService.getUserProduct(info.get().getId());

        assert (productList != null);

        assert(productList.size() > 0);
        assert(productList.get(0).getProdId() != null);
        assert(productList.get(0).getType() != null);
        assert(productList.get(0).getName() != null);
        assert(productList.get(0).getBalance() != null);
    }

    @Test
    public void getProductListWhenUserNotFoundTest() throws Exception {
        Optional<UserInfoEntity> info = Optional.empty();
        when(userInfoRepository.findById(any())).thenReturn(info);

        List<ProductInfo> productList =  userAccountRepositoryService.getUserProduct(6L);

        assert (productList == null);
    }

}
