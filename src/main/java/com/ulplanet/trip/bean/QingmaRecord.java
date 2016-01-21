package com.ulplanet.trip.bean;


/**
 * Created by makun on 2016/1/16.
 */
public class QingmaRecord implements java.io.Serializable{
    private static final long serialVersionUID = -3203921037101965153L;
    private String id;
    private String appId;
    private String accountId;
    private String clientNumber;
    private String clientPwd;
    private String callType;
    private String caller;
    private String called;
    private String fromSerNum;
    private String toSerNum;
    private String callId;
    private String startTime;
    private String stopTime;
    private String calledPickTime;
    private String byeType;
    private String callTime;
    private String reason;
    private String recordUrl;
    private String timestamp;
    private String sig;

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getCalledPickTime() {
        return calledPickTime;
    }

    public void setCalledPickTime(String calledPickTime) {
        this.calledPickTime = calledPickTime;
    }

    public String getByeType() {
        return byeType;
    }

    public void setByeType(String byeType) {
        this.byeType = byeType;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
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

    public String getClientPwd() {
        return clientPwd;
    }

    public void setClientPwd(String clientPwd) {
        this.clientPwd = clientPwd;
    }
}
