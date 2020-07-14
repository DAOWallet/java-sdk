package ru.daowallet.sdk.dto;

import java.io.Serializable;
import java.util.List;

public class BalanceResponse implements Serializable {
    private List<Balance> balance;

    public BalanceResponse() {
    }

    public BalanceResponse(List<Balance> balance) {
        this.balance = balance;
    }

    public List<Balance> getBalance() {
        return balance;
    }

    public void setBalance(List<Balance> balance) {
        this.balance = balance;
    }
}
