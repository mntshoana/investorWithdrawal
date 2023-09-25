package za.co.investorWithdrawal.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static BigDecimal fromStringToBigDecimal(String number){
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
        } catch (Exception e){
            return null;
        }
    }

    public static String fromBigDecimalToCurrency(BigDecimal number){

        BigDecimal rounded = number.round(new MathContext(19, RoundingMode.FLOOR));
        DecimalFormat formatter = new DecimalFormat("0.00");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(rounded);
    }

    public static String fromBigDecimalToRands(BigDecimal number){
        String value = fromBigDecimalToCurrency(number);
        if (value == null)
            value = "0.0";
        return String.format("R %s" , value );
    }
    public static String fromBigDecimalToRands(BigDecimal number, int width){
        String currency = fromBigDecimalToRands(number);
        int difference = width - currency.length() -1;
        return String.format("%-" + difference  + "%s" , currency);
    }

    public static int getAge(Date dob){
        Calendar curDate = Calendar.getInstance();
        Calendar dobAsCalendar = Calendar.getInstance();
        dobAsCalendar.setTime(dob);

        int potentialYear = dobAsCalendar.get(Calendar.YEAR) - curDate.get(Calendar.YEAR) -1;
        int potentialDays = dobAsCalendar.get(Calendar.DAY_OF_YEAR) - curDate.get(Calendar.DAY_OF_YEAR);
        if (potentialDays <= 0){
            potentialYear += 1;
        }
        return potentialYear;
    }

}
