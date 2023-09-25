package za.co.investorWithdrawal.service.repository.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name="useraccount")
@Entity(name="useraccount")
@Getter
@Setter
@Cacheable(false)
public class UserAccountEntity implements Serializable {
    @Id
    @Column(name="accountnumber")
    private Long accountNumber;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prodid", referencedColumnName = "prodid")
    private ProductTypeEntity productType;

    @ManyToOne(targetEntity = UserInfoEntity.class)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private UserInfoEntity user;

    private BigDecimal balance;
}
