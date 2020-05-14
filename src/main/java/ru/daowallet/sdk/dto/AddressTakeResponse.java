package ru.daowallet.sdk.dto;

import java.io.Serializable;

public class AddressTakeResponse implements Serializable {
    private long id;
    private String address;
    private String currency;
    private String foreign_id;
    private String tag;

    public AddressTakeResponse() {
    }

    public AddressTakeResponse(long id, String address, String currency, String foreign_id, String tag) {
        this.id = id;
        this.address = address;
        this.currency = currency;
        this.foreign_id = foreign_id;
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getForeign_id() {
        return foreign_id;
    }

    public void setForeign_id(String foreign_id) {
        this.foreign_id = foreign_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
