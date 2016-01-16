package com.ulplanet.trip.bean;

import java.util.Date;

/**
 * Created by makun on 2016/1/16.
 */
public class QingmaRecord {
    private String id;
    private String appId;
    private String clientNumber;
    private Integer callType;
    private String caller;
    private String called;
    private String fromSerNum;
    private String toSerNum;
    private String callId;
    private Date startTime;
    private Date stopTime;
    private Date calledPickTime;
    private String byeType;
    private Integer callTime;
    private String reason;
    private String recordUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getFromSerNum() {
        return fromSerNum;
    }

    public void setFromSerNum(String fromSerNum) {
        this.fromSerNum = fromSerNum;
    }

    public String getToSerNum() {
        return toSerNum;
    }

    public void setToSerNum(String toSerNum) {
        this.toSerNum = toSerNum;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Date getCalledPickTime() {
        return calledPickTime;
    }

    public void setCalledPickTime(Date calledPickTime) {
        this.calledPickTime = calledPickTime;
    }

    public String getByeType() {
        return byeType;
    }

    public void setByeType(String byeType) {
        this.byeType = byeType;
    }

    public Integer getCallTime() {
        return callTime;
    }

    public void setCallTime(Integer callTime) {
        this.callTime = callTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }
}
