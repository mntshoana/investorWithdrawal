package com.enviro.assessment.grad001.motsoaledineotshoana.service;

import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.UserInfoRepository;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.UserInfoRepositoryService;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.UserInfoEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap.UserInfo;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.enviro.assessment.grad001.motsoaledineotshoana.TestUtils.makeUserInfoEntity;

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
