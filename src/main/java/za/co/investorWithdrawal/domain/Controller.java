package za.co.investorWithdrawal.domain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import za.co.investorWithdrawal.constants.Constants;
import za.co.investorWithdrawal.data.domain.*;
import za.co.investorWithdrawal.service.ProductsService;
import za.co.investorWithdrawal.service.UserService;
import za.co.investorWithdrawal.service.WithdrawalService;

import javax.validation.constraints.*;


@RestController
public class Controller {

    @Autowired
    UserService userService;

    @Autowired
    ProductsService productsService;

    @Autowired
    WithdrawalService withdrawalService;

    @GetMapping(value = "api/v1/info",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve a user's personal information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(schema = @Schema(implementation = UserInfoResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request (invalid input)", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Error! Unable to find user with provided id", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "500", description = "General internal server error", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))})
    })
    public ResponseEntity<UserInfoResponseDTO> getInvestorInformation(@Parameter(description = "User id be searched")
                                                                      @Schema(type = "integer",
                                                                              minimum = "1", maximum = "" + Long.MAX_VALUE,
                                                                              example = "2", required = true,
                                                                              pattern = Constants.NUMBER_REGEX)
                                                                      @RequestParam() Long userId) {
        return userService.getInfo(userId);
    }

    @GetMapping(value = "api/v1/product/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve the list of products a user has invested in")
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
        return productsService.getProductList(userId);
    }

    @PostMapping(value = "api/v1/product/withdraw",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Withdraw an amount from an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request (invalid input)", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Error! Unable to find user with provided id", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "500", description = "General internal server error", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))})
    })
    public ResponseEntity<WithdrawalResponseDTO> withdraw(@RequestBody

                                                                  WithdrawalRequestDTO request) {
        return withdrawalService.withdraw(request);
    }
}
