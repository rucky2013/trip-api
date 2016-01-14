package com.ulplanet.trip.bean;

import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.util.LocalContext;
import com.ulplanet.trip.util.ValidatorUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by makun on 2016/1/14.
 */
public class CallRecord {
    private String id;
    private String code;
    private String telFunction;
    private String callType;
    private String phoneNumber;
    private String answerStatus;
    private String startDate;
    private String endDate;
    private String createUser;
    private String answerDate;
    private Date createDate;

    public void preInsert(){
        this.id = IdGen.uuid();
        this.createDate = new Date();
        this.createUser = LocalContext.getUser().getCode();
    }

    public String validator(){
        return ValidatorUtils.validate(this);
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty(message = "code不能为空")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotEmpty(message = "telFunction不能为空")
    public String getTelFunction() {
        return telFunction;
    }

    public void setTelFunction(String telFunction) {
        this.telFunction = telFunction;
    }

    @NotEmpty(message = "callType不能为空")
    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    @NotEmpty(message = "phoneNumber不能为空")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotEmpty(message = "answerStatus不能为空")
    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }

    @NotEmpty(message = "startDate不能为空")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @NotEmpty(message = "endDate不能为空")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
