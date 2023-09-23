package za.co.investorWithdrawal.service.repository.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name="userInfo")
@Entity
@Getter
@Setter
public class ProductTypeEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int prodId;

    @NotNull
    @Column(name="type")
    private String type;

    @Column(name="name")
    private String name;
}
