package za.co.investorWithdrawal.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UtilsTest {
    @Test
    public void BigDecimalConversion(){
        BigDecimal amount = Utils.fromStringToBigDecimal("1,234,567.90");
        assert(amount.toString().equals("1234567.90"));
        assert(Utils.fromBigDecimalToCurrency(amount).equals("1,234,567.90"));
    }

    @Test
    public void fromBigDecimalToCurrencyTest(){
        BigDecimal amount = Utils.fromStringToBigDecimal("1,234,567.90");
        String rands = Utils.fromBigDecimalToRands(amount);
        assert(rands.equals("R 1,234,567.90"));
    }

    @Test
    public void fromBigDecimalToRandsTest(){
        BigDecimal amount = Utils.fromStringToBigDecimal("1,234,567.90");
        String string = Utils.fromBigDecimalToRands(amount, 40);
        assert(string.length() == 40);
    }

    @Test
    public void getAgeTest() throws ParseException {

        Date dateNow = Utils.now();
        LocalDate date1Local = Utils.localNow();
        int daysInAYear = 365;
        if (date1Local.isLeapYear())
            daysInAYear = 364;

        Date dateLastYear = Utils.localToDate(date1Local.minusDays(daysInAYear));
        Date dateLastYearPlusOne = Utils.localToDate(date1Local.minusDays(daysInAYear -1));

        assert(Utils.getAge(dateNow) == 0);
        assert(Utils.getAge(dateLastYear) == 1);
        assert(Utils.getAge(dateLastYearPlusOne) == 0);
    }
}
