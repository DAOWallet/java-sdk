package ru.daowallet.sdk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.daowallet.sdk.serializer.DecimalSerializer;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawalCrypto extends AddressTake implements Serializable {
    @JsonSerialize(using = DecimalSerializer.class)
    private BigDecimal amount;
    private String address;

    public WithdrawalCrypto() {
    }

    public WithdrawalCrypto(String foreign_id, String currency, BigDecimal amount, String address) {
        super(foreign_id, currency);
        this.amount = amount;
        this.address = address;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
