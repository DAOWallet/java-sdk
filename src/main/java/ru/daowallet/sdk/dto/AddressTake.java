package ru.daowallet.sdk.dto;

import java.io.Serializable;

public class AddressTake implements Serializable {
    protected String foreign_id;
    protected String currency;

    public AddressTake(){

    }

    public AddressTake(String foreign_id, String currency) {
        this.foreign_id = foreign_id;
        this.currency = currency;
    }

    public String getForeign_id() {
        return foreign_id;
    }

    public void setForeign_id(String foreign_id) {
        this.foreign_id = foreign_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
