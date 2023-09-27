package za.co.investorWithdrawal.service;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class Utils {


    public static BigDecimal fromStringToBigDecimal(String number) {
        try {
            // from format x,xxx,xxx.dd
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');
            String pattern = "#,###.##";
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            decimalFormat.setDecimalFormatSymbols(symbols);

            decimalFormat.setParseBigDecimal(true);

            return (BigDecimal) decimalFormat.parse(number);
        } catch (Exception e) {
            return null;
        }
    }

    public static String fromBigDecimalToCurrency(BigDecimal number) {

        BigDecimal rounded = number.round(new MathContext(19, RoundingMode.FLOOR));
        DecimalFormat formatter = new DecimalFormat("0.00");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setGroupingSize(3);
        formatter.setGroupingUsed(true);
        return formatter.format(rounded);
    }

    public static String fromBigDecimalToRands(BigDecimal number) {
        String value = fromBigDecimalToCurrency(number);
        if (value == null)
            value = "0.0";
        return String.format("R %s", value);
    }

    public static String fromBigDecimalToRands(BigDecimal number, int width) {
        String currency = fromBigDecimalToCurrency(number);
        int difference = width - 1;
        String initialFormat = String.format("%d", difference);
        initialFormat = "%" + initialFormat + "s";
        return "R" + String.format(initialFormat, currency);
    }

    public static long getAge(Date dob) {
        LocalDate start = dob.toInstant().atZone(ZoneId.ofOffset("UTC", ZoneOffset.UTC)).toLocalDate();
        LocalDate now = localNow();
        long years = ChronoUnit.YEARS.between(start, now);

        return years;
    }

    public static LocalDate localNow() {
        return LocalDate.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC));
    }

    public static Date now() {
        return Date.from(localNow().atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    public static Date localToDate(LocalDate date) {
        return Date.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));

    }

    public static LocalDateTime localTimeNow() {
        return LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC));
    }
}
