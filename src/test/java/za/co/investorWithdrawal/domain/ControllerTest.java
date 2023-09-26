package za.co.investorWithdrawal.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import za.co.investorWithdrawal.data.domain.*;
import za.co.investorWithdrawal.service.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static za.co.investorWithdrawal.TestUtils.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @Mock
    ProductsService productsService;

    @Mock
    WithdrawalService withdrawalService;

    @InjectMocks
    Controller controller;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getInvestorInformationTest() throws Exception {
        ResponseEntity<UserInfoResponseDTO> response = makeUserInfoResponse();
        when(userService.getInfo(any())).thenReturn(response);

        String url = "http://127.0.0.1:8443/api/v1/info?userId=2";

        this.mockMvc.perform(get(url))
                .andDo(print())
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

    @Test
    public void getProductListForInvestorTest() throws Exception {
        ResponseEntity<ProductInfoResponseDTO> response = makeProductInfoResponse();
        when(productsService.getProductList(any())).thenReturn(response);

        String url = "http://127.0.0.1:8443/api/v1/product/list?userId=2";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].prodId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].type").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].balance").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value("Success"));
    }

    @Test
    public void withdrawTest() throws Exception {
        WithdrawalRequestDTO request = makeWithdrawalRequest();
        when(withdrawalService.withdraw(any())).thenReturn(makeWithdrawalResponse(request));

        String url = "http://127.0.0.1:8443/api/v1/product/withdraw";

        String body = new ObjectMapper().writeValueAsString(request);

        this.mockMvc.perform(
                        post(url)
                                .content(body)
                                .header("Content-Type", "application/json")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.openingBalance").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.closingBalance").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value("Success"));
    }

}
