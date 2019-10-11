package me.pyradian.ojackpayment.model;

public class BasicResponse<T> {
    private String status_code; // HTTP Response code
    private String status_msg;
    private T content;

    public BasicResponse(String status_code, String status_msg, T content) {
        this.status_code = status_code;
        this.status_msg = status_msg;
        this.content = content;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
