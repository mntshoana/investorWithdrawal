package za.co.investorWithdrawal.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.investorWithdrawal.service.repository.entity.WithdrawalEntity;

import java.util.List;


@Repository
public interface WithdrawalRepository extends JpaRepository<WithdrawalEntity, Long> {
    @Query(value="SELECT w FROM withdrawal w " +
            "WHERE w.status.statusId IN :statusIdList")
    @Transactional(readOnly = true)
    List<WithdrawalEntity> findAllByStatusStatus(@Param("statusIdList") Integer statusIdList);
}