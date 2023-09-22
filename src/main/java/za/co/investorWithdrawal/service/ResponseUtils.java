package za.co.investorWithdrawal.service;

import org.springframework.http.HttpStatus;
import za.co.investorWithdrawal.data.domain.ResponseMessageDTO;

public class ResponseUtils {
    public static ResponseMessageDTO successResponse() {
        return ResponseMessageDTO.builder()
                .code(HttpStatus.OK.value())
                .description("Success")
                .build();
    }

    public static ResponseMessageDTO systemError() {
        return ResponseMessageDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .description("Error! Something went wrong with our system. Please try again later.")
                .build();
    }
}
