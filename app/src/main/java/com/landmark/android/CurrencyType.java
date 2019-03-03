package com.landmark.android;

public enum CurrencyType {
    INR("INR"),
    AED("AED"),
    SAR("SAR");

    private final String currencyType;

    CurrencyType(String currencyType) {
        this.currencyType= currencyType;
    }
}
