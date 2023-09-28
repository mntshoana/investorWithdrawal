package za.co.investorWithdrawal.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.investorWithdrawal.service.repository.entity.UserAccountEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    List<UserAccountEntity> findAllByUserId(Long userId);

    @Query(value="SELECT w FROM useraccount w " +
            "WHERE w.user.id = :userId AND w.accountNumber = :prodId")
    @Transactional(readOnly = true)
    Optional<UserAccountEntity> findBy(@Param("userId") Long userId, @Param("prodId") Long prodId);
}