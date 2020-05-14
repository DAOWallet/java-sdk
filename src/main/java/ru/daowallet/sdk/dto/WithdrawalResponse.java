package ru.daowallet.sdk.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawalResponse implements Serializable {
    private String foreign_id;
    private String type;
    private BigDecimal amount;
    private String sender_currency;
    private String receiver_currency;

    public WithdrawalResponse() {
    }

    public WithdrawalResponse(String foreign_id, String type, BigDecimal amount, String sender_currency, String receiver_currency) {
        this.foreign_id = foreign_id;
        this.type = type;
        this.amount = amount;
        this.sender_currency = sender_currency;
        this.receiver_currency = receiver_currency;
    }

    public String getForeign_id() {
        return foreign_id;
    }

    public void setForeign_id(String foreign_id) {
        this.foreign_id = foreign_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSender_currency() {
        return sender_currency;
    }

    public void setSender_currency(String sender_currency) {
        this.sender_currency = sender_currency;
    }

    public String getReceiver_currency() {
        return receiver_currency;
    }

    public void setReceiver_currency(String receiver_currency) {
        this.receiver_currency = receiver_currency;
    }
}
