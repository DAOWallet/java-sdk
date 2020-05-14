package ru.daowallet.sdk.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceAddress implements Serializable {
    private String address;
    private BigDecimal expected_amount;
    private String crypto_currency;
    private Double rate_usd;
    private Double rate_eur;

    public InvoiceAddress() {
    }

    public InvoiceAddress(String address, BigDecimal expected_amount, String crypto_currency, Double rate_usd, Double rate_eur) {
        this.address = address;
        this.expected_amount = expected_amount;
        this.crypto_currency = crypto_currency;
        this.rate_usd = rate_usd;
        this.rate_eur = rate_eur;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getExpected_amount() {
        return expected_amount;
    }

    public void setExpected_amount(BigDecimal expected_amount) {
        this.expected_amount = expected_amount;
    }

    public String getCrypto_currency() {
        return crypto_currency;
    }

    public void setCrypto_currency(String crypto_currency) {
        this.crypto_currency = crypto_currency;
    }

    public Double getRate_usd() {
        return rate_usd;
    }

    public void setRate_usd(Double rate_usd) {
        this.rate_usd = rate_usd;
    }

    public Double getRate_eur() {
        return rate_eur;
    }

    public void setRate_eur(Double rate_eur) {
        this.rate_eur = rate_eur;
    }
}
