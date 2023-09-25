package za.co.investorWithdrawal.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.investorWithdrawal.data.UserContactInfo;
import za.co.investorWithdrawal.data.UserInfo;
import za.co.investorWithdrawal.data.UserPersonalInfo;
import za.co.investorWithdrawal.service.repository.entity.UserInfoEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class UserInfoRepositoryService {

    @Autowired
    UserInfoRepository userInfoRepository;

    public UserInfo getUser(Long userId) {
        Optional<UserInfoEntity> possibleUser = userInfoRepository.findById(userId);
        if (possibleUser.isPresent()) {
            UserInfoEntity user = possibleUser.get();

            UserPersonalInfo personal = makePersonalUser(user);
            String address = makeAddress(user);
            UserContactInfo contact = makeContactInfo(user);

            return UserInfo.builder()
                    .personal(personal)
                    .address(address)
                    .contact(contact)
                    .build();
        } else {
            return null;
        }
    }

    private UserPersonalInfo makePersonalUser(UserInfoEntity user) {
        return  UserPersonalInfo.builder()
                .name(user.getFirstName()
                        + ((user.getMiddleName() != null) ? user.getMiddleName() : ""))
                .surname(user.getLastName())
                .dateOfBirth(makeDate(user.getDateOfBirth()))
                .build();
    }
    private String makeDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        return (strDate);
    }

    private String makeAddress(UserInfoEntity user) {
        return ((user.getAddressLine1() != null) ? user.getAddressLine1() + "," + System.lineSeparator() : "")
                + ((user.getAddressLine2() != null) ? user.getAddressLine2() + "," + System.lineSeparator() : "")
                + ((user.getCity() != null) ? user.getCity() + "," + System.lineSeparator() : "")
                + ((user.getCountry() != null) ? user.getCountry() + "," + System.lineSeparator() : "")
                + ((user.getZipCode() != null) ? user.getZipCode() + System.lineSeparator() : "");
    }

    private UserContactInfo makeContactInfo(UserInfoEntity user) {
        return UserContactInfo.builder()
                .cell(user.getCell())
                .email(user.getEmail())
                .build();
    }
}
