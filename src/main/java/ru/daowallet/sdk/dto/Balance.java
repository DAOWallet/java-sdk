package ru.daowallet.sdk.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Balance implements Serializable {
    private String currency_name;
    private BigDecimal value;

    public Balance() {
    }

    public Balance(String currency_name, BigDecimal value) {
        this.currency_name = currency_name;
        this.value = value;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
