package ru.daowallet.sdk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.daowallet.sdk.serializer.MoneyDoubleSerializer;

import java.io.Serializable;

public class Invoice implements Serializable {
    @JsonSerialize(using = MoneyDoubleSerializer.class)
    private Double amount;
    private String fiat_currency;

    public Invoice() {
    }

    public Invoice(Double amount, String fiat_currency) {
        this.amount = amount;
        this.fiat_currency = fiat_currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFiat_currency() {
        return fiat_currency;
    }

    public void setFiat_currency(String fiat_currency) {
        this.fiat_currency = fiat_currency;
    }
}
