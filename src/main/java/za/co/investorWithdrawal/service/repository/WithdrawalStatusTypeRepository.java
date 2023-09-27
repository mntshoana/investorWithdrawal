package za.co.investorWithdrawal.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.investorWithdrawal.service.repository.entity.WithdrawalStatusTypeEntity;


@Repository
public interface WithdrawalStatusTypeRepository extends JpaRepository<WithdrawalStatusTypeEntity, Integer> {

}