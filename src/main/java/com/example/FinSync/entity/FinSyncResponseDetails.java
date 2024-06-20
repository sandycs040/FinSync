package com.example.FinSync.entity;

public class FinSyncResponseDetails {
    private String statusCode;
    private String status;
    private String message;
    private Object data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FinSyncResponseDetails(String statusCode,String message, Object data) {
        super();
        if(statusCode.equalsIgnoreCase("200")) {
            this.status="success";
            this.message=message;
            if(data instanceof String) {
                this.message = (String) data;
            }else {
                this.data = data;
            }
        }else {
            this.message=message;
            this.status="error";
            if(statusCode.equalsIgnoreCase("400")){
                this.data = data;
            }
        }
        this.statusCode = statusCode;

    }

    @Override
    public String toString() {
        return "{" +
                "statusCode='" + statusCode + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
