package za.co.investorWithdrawal.service.repository.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="withdrawalstatustype")
@Entity(name="withdrawalstatustype")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalStatusTypeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="statusid")
    @NotNull
    private Integer statusId;

    @NotNull
    private String description;
}
