package com.enviro.assessment.grad001.motsoaledineotshoana.service;

import com.enviro.assessment.grad001.motsoaledineotshoana.TestUtils;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap.ProductInfo;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.UserAccountRepository;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.UserAccountRepositoryService;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.UserInfoRepository;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        Optional<UserInfoEntity> info = Optional.of(TestUtils.makeUserInfoEntity());
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
