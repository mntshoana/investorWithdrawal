package za.co.investorWithdrawal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import za.co.investorWithdrawal.data.UserInfo;
import za.co.investorWithdrawal.data.domain.UserInfoResponseDTO;
import za.co.investorWithdrawal.service.repository.UserInfoRepositoryService;

@Service
public class UserService {

    @Autowired
    UserInfoRepositoryService userInfoRepositoryService;

    public ResponseEntity<UserInfoResponseDTO> getInfo(Long userId) {
        try {
            UserInfo user = userInfoRepositoryService.getUser(userId);
            if (user != null)
                return new ResponseEntity<>(UserInfoResponseDTO.builder()
                        .user(user)
                        .response(ResponseUtils.successResponse())
                        .build(), HttpStatus.OK);
            else {
                return new ResponseEntity<>(UserInfoResponseDTO.builder()
                        .error(ResponseUtils.notFoundError("Error! Unable to find user with provided id"))
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(UserInfoResponseDTO.builder()
                    .error(ResponseUtils.systemError())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
