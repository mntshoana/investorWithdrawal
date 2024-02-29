package com.enviro.assessment.grad001.motsoaledineotshoana.domain;

import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.ProductInfoResponseDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.ResponseMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.enviro.assessment.grad001.motsoaledineotshoana.constants.Constants;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.ProductsService;


@RestController
@Tag(name="Product")
public class ProductController {

    @Autowired
    ProductsService productsService;

    @GetMapping(value = "api/v1/products",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve the list of products a user has invested in")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request (invalid input)", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Error! Unable to find user with provided id", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "500", description = "General internal server error", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))})
    })
    public ResponseEntity<ProductInfoResponseDTO> getProductListForInvestor(@Parameter(description = "User id be searched")
                                                                            @Schema(type = "integer",
                                                                                    minimum = "1", maximum = "" + Long.MAX_VALUE,
                                                                                    example = "2", required = true,
                                                                                    pattern = Constants.NUMBER_REGEX)
                                                                            @RequestParam Long userId) {
        System.out.println("Running");
        return productsService.getProductList(userId);
    }
}
