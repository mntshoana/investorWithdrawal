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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.enviro.assessment.grad001.motsoaledineotshoana.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ProductContollerTest {
    private MockMvc mockMvc;

    @Mock
    ProductsService productsService;

    @InjectMocks
    ProductController productController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void getProductListForInvestorTest() throws Exception {
        ResponseEntity<ProductInfoResponseDTO> response = makeProductInfoResponse();
        when(productsService.getProductList(any())).thenReturn(response);

        String url = "/api/v1/products";

        this.mockMvc.perform(get(url)
                        .param("userId","2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].prodId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].type").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].balance").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.description").value("Success"));
    }

}
