package za.co.investorWithdrawal.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.investorWithdrawal.service.repository.entity.UserAccountEntity;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    List<UserAccountEntity> findAllByUserId(Long userId);
}