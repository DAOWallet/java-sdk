package ru.daowallet.sdk.dto;

import java.io.Serializable;

public class ResponseOK implements Serializable {
    private Object data;

    public ResponseOK() {
    }

    public ResponseOK(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
