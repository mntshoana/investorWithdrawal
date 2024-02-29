package com.enviro.assessment.grad001.motsoaledineotshoana.service;

import com.enviro.assessment.grad001.motsoaledineotshoana.resource.bootstrap.WithdrawalResult;
import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.WithdrawalResponseDTO;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.WithdrawalRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enviro.assessment.grad001.motsoaledineotshoana.resource.dto.WithdrawalRequestDTO;
import org.springframework.util.MultiValueMap;

import java.util.Date;

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

    public ResponseEntity<byte[]> listWithdrawals(Long userId, Long prodId, Date dateFrom, Date dateTo) {
        try {
            byte [] result;
            if (dateTo != null && dateFrom != null)
                result = withdrawalRepositoryService.listWithdrawals(userId, prodId, dateFrom, dateTo);
            else if (dateFrom != null)
                result = withdrawalRepositoryService.listWithdrawals(userId, prodId, dateFrom);
            else
                result = withdrawalRepositoryService.listWithdrawals(userId, prodId);
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + prodId + ".csv");
            if (result != null && result.length > 0) {
                return new ResponseEntity<>(result, headers, HttpStatus.OK);
            } else {
                // length == 0 is no withdrawal history / user not found / accountnumber not found
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
