package za.co.investorWithdrawal.service;

import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import za.co.investorWithdrawal.data.UserInfo;
import za.co.investorWithdrawal.data.domain.UserInfoResponseDTO;

import java.util.Date;

@Service
public class UserService {
    public ResponseEntity<UserInfoResponseDTO> getInfo(String userId) {
        try {
            // (if user exists)
            UserInfo user = UserInfo.builder()
                    .name("name")
                    .surname("surname")
                    .dateOfBirth(new Date())
                    .cell("0123456789")
                    .email("email@address.com")
                    .build();
            // else null
            return new ResponseEntity<>(UserInfoResponseDTO.builder()
                    .user(user)
                    .response(ResponseUtils.successResponse())
                    .build(), HttpStatus.OK);
            // else
            // return an error "Error! Unable to find user with provided id"
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<UserInfoResponseDTO>(UserInfoResponseDTO.builder()
                    .response(ResponseUtils.systemError())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
