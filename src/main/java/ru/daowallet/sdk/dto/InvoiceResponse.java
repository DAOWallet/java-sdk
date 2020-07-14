package ru.daowallet.sdk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InvoiceResponse implements Serializable {
    private String foreign_id;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date expired_at;//":"2020-05-12T19:05:55.057Z",
    private Double client_amount;
    private String client_currency;
    private List<InvoiceAddress> addresses;

    public InvoiceResponse() {
    }

    public InvoiceResponse(String foreign_id, String status, Date expired_at, Double client_amount, String client_currency, List<InvoiceAddress> addresses) {
        this.foreign_id = foreign_id;
        this.status = status;
        this.expired_at = expired_at;
        this.client_amount = client_amount;
        this.client_currency = client_currency;
        this.addresses = addresses;
    }

    public String getForeign_id() {
        return foreign_id;
    }

    public void setForeign_id(String foreign_id) {
        this.foreign_id = foreign_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(Date expired_at) {
        this.expired_at = expired_at;
    }

    public Double getClient_amount() {
        return client_amount;
    }

    public void setClient_amount(Double client_amount) {
        this.client_amount = client_amount;
    }

    public String getClient_currency() {
        return client_currency;
    }

    public void setClient_currency(String client_currency) {
        this.client_currency = client_currency;
    }

    public List<InvoiceAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<InvoiceAddress> addresses) {
        this.addresses = addresses;
    }
}
