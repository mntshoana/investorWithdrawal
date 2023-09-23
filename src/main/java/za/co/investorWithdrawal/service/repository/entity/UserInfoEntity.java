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
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name="firstname")
    private String firstName;

    @Column(name="middlename")
    private String middleName;

    @NotNull
    @Column(name="lastname")
    private String lastName;

    @NotNull
    private String cell;

    @NotNull
    private String email;

    @Column(name="addressLine1")
    private String addressLine1;
    @Column(name="addressLine2")
    private String addressLine2;
    @Column(name="city")
    private String city;
    @Column(name="country")
    private String countryWork;
    @Column(name="postalCode")
    private String zipCodeWork;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="userAccount", referencedColumnName = "accountNumber")
    @ElementCollection(targetClass=String.class)
    private List<String> accountNumberList;
}
