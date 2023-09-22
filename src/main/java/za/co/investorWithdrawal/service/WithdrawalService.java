package za.co.investorWithdrawal.service;

import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import za.co.investorWithdrawal.data.WithdrawalResponse;
import za.co.investorWithdrawal.data.domain.WithdrawalRequestDTO;
import za.co.investorWithdrawal.data.domain.WithdrawalResponseDTO;

import java.math.BigDecimal;

@Service
public class WithdrawalService {
    public ResponseEntity withdraw(WithdrawalRequestDTO request) {
        try {
            //  return new ResponseEntity<>("{prodId, amount}", HttpStatus.OK);
            // (if verification success)
            WithdrawalResponse example = WithdrawalResponse.builder()
                    .prodId(request.getProdId())
                    .amount(new BigDecimal(1))
                    .build();

            // else null
            return new ResponseEntity<>(WithdrawalResponseDTO.builder()
                    .withdrawn(example)
                    .response(ResponseUtils.successResponse())
                    .build(), HttpStatus.OK);
            // else
            // return an error "Error! Unable to find user with provided id"
        } catch (
                Exception e) {
            e.printStackTrace();
            return new ResponseEntity<WithdrawalResponseDTO>(WithdrawalResponseDTO.builder()
                    .response(ResponseUtils.systemError())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
