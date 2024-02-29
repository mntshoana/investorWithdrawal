package com.enviro.assessment.grad001.motsoaledineotshoana.domain;

import com.enviro.assessment.grad001.motsoaledineotshoana.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.ResponseMessageDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.WithdrawalRequestDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.WithdrawalResponseDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.WithdrawalService;

import java.util.Date;


@RestController
@Tag(name="Withdrawal")
public class WithdrawalController {
    @Autowired
    WithdrawalService withdrawalService;

    @PostMapping(value = "api/v1/products/withdrawal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Withdraw an amount from an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request (invalid input)", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Error! Unable to find user with provided id", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "500", description = "General internal server error", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))})
    })
    public ResponseEntity<WithdrawalResponseDTO> withdraw(@RequestBody WithdrawalRequestDTO request) {
        return withdrawalService.withdraw(request);
    }


    @GetMapping(value = "api/v1/products/withdrawal/list",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Retrieves a list of all the withdrawals from an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request (invalid input)", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Error! Unable to find account with provided account number", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))}),
            @ApiResponse(responseCode = "500", description = "General internal server error", content = {@Content(schema = @Schema(implementation = ResponseMessageDTO.class))})
    })
    public ResponseEntity<byte[]> listWithdrawals(@Parameter(description = "User id be searched")
                                                              @Schema(type = "integer",
                                                                      minimum = "1", maximum = "" + Long.MAX_VALUE,
                                                                      example = "2", required = true,
                                                                      pattern = Constants.NUMBER_REGEX)
                                                              @RequestParam Long userId,
                                                          @Parameter(description = "prodId to be searched")
                                                              @Schema(type = "integer",
                                                                      minimum = "1", maximum = "" + Long.MAX_VALUE,
                                                                      example = "2", required = true,
                                                                      pattern = Constants.NUMBER_REGEX)
                                                              @RequestParam Long prodId,
                                                          @Parameter(description = "Date lower-bound to be used to filter")
                                                              @Schema(type = "date",
                                                                      example = "01/01/2022", required = false
                                                                     )
                                                              @Nullable
                                                              @RequestParam Date dateFrom,
                                                          @Parameter(description = "Date upper-bound to be used to filter")
                                                              @Schema(type = "date",
                                                                      example = "01/01/2022", required = false
                                                                     )
                                                              @Nullable
                                                              @RequestParam Date dateTo) {
        return withdrawalService.listWithdrawals(userId, prodId, dateFrom, dateTo);
    }
}
