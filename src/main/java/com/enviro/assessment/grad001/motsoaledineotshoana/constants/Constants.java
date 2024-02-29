package com.enviro.assessment.grad001.motsoaledineotshoana.constants;

public class Constants {
    public static final String NUMBER_REGEX = "[1-9]+[0-9]*" ;
    public static final String ACCOUNT_REGEX = "[0-9]{5,11}" ; // not enforced in backend yet
    public static final String BIG_DECIMAL_REGEX = "[1-9]{1,3}(\\,[1-9]{3})?(\\.[0-9]{1,2})?";
    public static final String DATE_REGEX = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
}
