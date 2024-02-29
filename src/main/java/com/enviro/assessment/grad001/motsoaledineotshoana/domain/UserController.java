package com.enviro.assessment.grad001.motsoaledineotshoana.domain;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.enviro.assessment.grad001.motsoaledineotshoana.constants.Constants;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.ResponseMessageDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.UserInfoResponseDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.UserService;

@RestController()
@Tag(name= "User")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "api/v1/investors/info",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve a user's personal information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
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
}
