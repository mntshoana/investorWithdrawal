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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static com.enviro.assessment.grad001.motsoaledineotshoana.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class WithdrawalControllerTest {
    private MockMvc mockMvc;

    @Mock
    WithdrawalService withdrawalService;

    @InjectMocks
    WithdrawalController withdrawalController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(withdrawalController).build();
    }

    @Test
    public void withdrawTest() throws Exception {
        WithdrawalRequestDTO request = makeWithdrawalRequest();
        when(withdrawalService.withdraw(any())).thenReturn(makeWithdrawalResponse(request));

        String url = "/api/v1/products/withdrawal";

        String body = new ObjectMapper().writeValueAsString(request);

        this.mockMvc.perform(
                        post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                                .characterEncoding(Charset.defaultCharset())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.openingBalance").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.closingBalance").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value("Success"));
    }

    @Test
    public void withdrawalListTest() throws Exception {
        WithdrawalRequestDTO request = makeWithdrawalRequest();
        ResponseEntity<byte[]> response = makeWithdrawalListResponse();
        when(withdrawalService.listWithdrawals(any(), any(), any(), any())).thenReturn(response);

        String url = "/api/v1/products/withdrawal/list";

        String body = new ObjectMapper().writeValueAsString(request);

        this.mockMvc.perform(
                        get(url)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                                .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                                .content(body)
                                .characterEncoding(Charset.defaultCharset())
                                .param("userId", "2")
                                .param("prodId", "484757332949")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(MockMvcResultMatchers.content().bytes(response.getBody()));
    }

}
