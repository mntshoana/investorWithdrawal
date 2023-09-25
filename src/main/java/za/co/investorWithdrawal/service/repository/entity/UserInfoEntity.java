package za.co.investorWithdrawal.service.repository.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

import java.util.LinkedList;
import java.util.List;

@Table(name = "userinfo")
@Entity(name="userinfo")
@Getter
@Setter
public class UserInfoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "firstname")
    private String firstName;

    @Column(name = "middlename")
    private String middleName;

    @NotNull
    @Column(name = "lastname")
    private String lastName;

    @NotNull
    @Column(name = "dob")
    private Date dateOfBirth;

    @NotNull
    private String cell;

    @NotNull
    private String email;

    @Column(name = "addressline1")
    private String addressLine1;

    @Column(name = "addressline2")
    private String addressLine2;
    private String city;
    private String country;
    @Column(name = "postalcode")
    private String zipCode;

    @Transient
    @ElementCollection
    @OneToMany(targetEntity = UserInfoEntity.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "userid")
    private List<UserAccountEntity> accountList = new LinkedList<>();

}
