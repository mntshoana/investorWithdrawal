package za.co.investorWithdrawal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import za.co.investorWithdrawal.data.WithdrawalResult;
import za.co.investorWithdrawal.data.domain.WithdrawalRequestDTO;
import za.co.investorWithdrawal.data.domain.WithdrawalResponseDTO;
import za.co.investorWithdrawal.service.repository.WithdrawalRepositoryService;


@Service
public class WithdrawalService {
    @Autowired
    WithdrawalRepositoryService withdrawalRepositoryService;


    public ResponseEntity withdraw(WithdrawalRequestDTO request) {
        try {
            WithdrawalResult result = withdrawalRepositoryService.withdraw(request);
            if (result != null) {
                if (result.isSuccessful())
                    return new ResponseEntity<>(WithdrawalResponseDTO.builder()
                            .prodId(result.getProdId())
                            .amount(result.getAmount())
                            .openingBalance(result.getOpeningBalance())
                            .closingBalance(result.getClosingBalance())
                            .response(ResponseUtils.successResponse())
                            .build(), HttpStatus.OK);
                else if (result.getOpeningBalance() == null)
                    return new ResponseEntity<>(WithdrawalResponseDTO.builder()
                            .prodId(result.getProdId())
                            .amount(result.getAmount())
                            .errorMessage(ResponseUtils.badRequest(result.getErrorMessage()))
                            .build(), HttpStatus.BAD_REQUEST);
                else
                    return new ResponseEntity<>(WithdrawalResponseDTO.builder()
                            .prodId(result.getProdId())
                            .amount(result.getAmount())
                            .openingBalance(result.getOpeningBalance())
                            .closingBalance(result.getClosingBalance())
                            .errorMessage(ResponseUtils.systemError(result.getErrorMessage()))
                            .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            } else
                // result is null == User not found
                return new ResponseEntity<>(WithdrawalResponseDTO.builder()
                        .errorMessage(ResponseUtils.notFoundError("Error! Unable to find user with provided id"))
                        .build(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(WithdrawalResponseDTO.builder()
                    .errorMessage(ResponseUtils.systemError())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
