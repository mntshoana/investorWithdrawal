package com.enviro.assessment.grad001.motsoaledineotshoana.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.enviro.assessment.grad001.motsoaledineotshoana.service.repository.entity.WithdrawalEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Repository
public interface WithdrawalRepository extends JpaRepository<WithdrawalEntity, Long> {
    @Query(value="SELECT w FROM withdrawal w " +
            "WHERE w.status.statusId IN :statusIdList")
    @Transactional(readOnly = true)
    List<WithdrawalEntity> findAllByStatusStatus(@Param("statusIdList") Integer statusIdList);

    @Query(value="SELECT w FROM withdrawal w " +
            "WHERE w.user.id = :userId AND w.account.accountNumber = :prodId")
    @Transactional(readOnly = true)
    List<WithdrawalEntity> findAllByUserProduct(@Param("userId") Long userId, @Param("prodId") Long prodId);

    @Query(value="SELECT w FROM withdrawal w " +
            "WHERE w.user.id = :userId AND w.account.accountNumber = :prodId" +
            " AND w.modified > :dateFrom")
    @Transactional(readOnly = true)
    List<WithdrawalEntity> findAllByUserProduct(@Param("userId") Long userId, @Param("prodId") Long prodId, @Param("dateFrom") LocalDateTime dateFrom);
    @Query(value="SELECT w FROM withdrawal w " +
            "WHERE w.user.id = :userId AND w.account.accountNumber = :prodId" +
            " AND w.modified BETWEEN :dateFrom AND :dateTo")
    @Transactional(readOnly = true)
    List<WithdrawalEntity> findAllByUserProduct(@Param("userId") Long userId, @Param("prodId") Long prodId, @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
}