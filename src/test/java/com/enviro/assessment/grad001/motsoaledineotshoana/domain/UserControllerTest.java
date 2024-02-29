package com.enviro.assessment.grad001.motsoaledineotshoana.domain;

import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.ProductInfoResponseDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.UserInfoResponseDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.WithdrawalRequestDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.ProductsService;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.UserService;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.WithdrawalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.enviro.assessment.grad001.motsoaledineotshoana.TestUtils.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getInvestorInformationTest() throws Exception {
        ResponseEntity<UserInfoResponseDTO> response = makeUserInfoResponse();
        when(userService.getInfo(any())).thenReturn(response);

        String url = "/api/v1/investors/info?userId=2";

        this.mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.personal.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.personal.surname").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.personal.dateOfBirth").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.address").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.contact.email").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.contact.cell").isNotEmpty())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.response.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value("Success"));
    }
}
