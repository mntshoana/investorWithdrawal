package com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import org.springframework.lang.Nullable;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap.UserInfo;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoResponseDTO {
    @Nullable
    private UserInfo user;
    @Nullable
    private ResponseMessageDTO response;
    @Nullable
    private ResponseMessageDTO error;

}
