package za.co.investorWithdrawal.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import za.co.investorWithdrawal.data.UserInfo;
import za.co.investorWithdrawal.service.repository.UserInfoRepository;
import za.co.investorWithdrawal.service.repository.UserInfoRepositoryService;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static za.co.investorWithdrawal.TestUtils.makeUserInfoEntity;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserInfoRepositoryService userInfoRepositoryService;

    @Mock
    UserInfoRepository userInfoRepository;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getUserInfotWhenUserFoundTest() throws Exception {
        Optional<UserInfoEntity> info = Optional.of(makeUserInfoEntity());
        when(userInfoRepository.findById(any())).thenReturn(info);


        UserInfo result =  userInfoRepositoryService.getUser(info.get().getId());

        assert (result != null);

        assert(result.getPersonal() != null);
        assert(result.getPersonal().getName() != null);
        assert(result.getPersonal().getSurname() != null);
        assert(result.getPersonal().getDateOfBirth() != null);

        assert(result.getAddress() != null);

        assert(result.getContact() != null);
        assert(result.getContact().getCell() != null);
        assert(result.getContact().getEmail()!= null);

    }

}
