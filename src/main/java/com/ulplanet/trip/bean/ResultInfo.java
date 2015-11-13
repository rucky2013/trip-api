package com.ulplanet.trip.bean;

/**
 * Created by makun on 2015/9/21.
 */
public class ResultInfo {
    private int status = -1;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
