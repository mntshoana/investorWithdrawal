package za.co.investorWithdrawal.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
}
