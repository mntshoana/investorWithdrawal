package za.co.investorWithdrawal.service.repository.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="producttype")
@Entity(name = "producttype")
@Getter
@Setter
public class ProductTypeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="prodid")
    private Integer prodId;

    @NotNull
    private String type;

    @NotNull
    private String description;
}
