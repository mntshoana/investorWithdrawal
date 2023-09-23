package za.co.investorWithdrawal.service.repository.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Table(name="userInfo")
@Entity
@Getter
@Setter
public class UserAccountEntity {
    @Id
    private String accountNumber;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prodId", referencedColumnName = "prodId")
    private ProductTypeEntity productType;

    @Column(name="balance")
    private BigDecimal balance;
}
