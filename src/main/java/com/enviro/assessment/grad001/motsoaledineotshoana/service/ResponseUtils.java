package com.enviro.assessment.grad001.motsoaledineotshoana.service;

import org.springframework.http.HttpStatus;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.ResponseMessageDTO;

public class ResponseUtils {
    public static ResponseMessageDTO successResponse() {
        return ResponseMessageDTO.builder()
                .code(HttpStatus.OK.value())
                .description("Success")
                .timestamp(Utils.localTimeNow())
                .build();
    }

    public static ResponseMessageDTO systemError() {
        return ResponseMessageDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .description("Error! Something went wrong with our system. Please try again later.")
                .timestamp(Utils.localTimeNow())
                .build();
    }

    public static ResponseMessageDTO systemError(String message) {
        return ResponseMessageDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .description(message)
                .timestamp(Utils.localTimeNow())
                .build();
    }
    public static ResponseMessageDTO badRequest(String message) {
        return ResponseMessageDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .description(message)
                .timestamp(Utils.localTimeNow())
                .build();
    }

    public static ResponseMessageDTO notFoundError(String message) {
        return ResponseMessageDTO.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .description(message)
                .timestamp(Utils.localTimeNow())
                .build();
    }
}
