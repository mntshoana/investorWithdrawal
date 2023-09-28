package za.co.investorWithdrawal.service.repository.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import za.co.investorWithdrawal.service.Utils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name="withdrawal")
@Entity(name="withdrawal")
@Getter
@Setter
@Cacheable(false)
@EntityListeners(AuditingEntityListener.class)
public class WithdrawalEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountnumber", referencedColumnName = "accountnumber")
    private UserAccountEntity account;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status", referencedColumnName = "statusid")
    private WithdrawalStatusTypeEntity status;

    @NotNull
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime modified;

    @NotNull
    private BigDecimal amount;

   @NotNull
   @Column(name="previousbalance")
    private BigDecimal previousBalance;

    @PrePersist
    public void onPrePersist() {
        setCreated(Utils.localTimeNow());
    }


    @PreUpdate
    public void onPreUpdate() {
        setModified(Utils.localTimeNow());
    }

}
