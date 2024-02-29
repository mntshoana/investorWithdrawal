package com.enviro.assessment.grad001.motsoaledineotshoana.constants;

public enum ProductType {
    RETIREMENT, SAVINGS;

    public static ProductType of(String type){
        switch (type){
            case "RETIREMENT":
                return ProductType.RETIREMENT;
            case "SAVINGS":
                return ProductType.SAVINGS;
            default:
                return null;
        }
    }
}
