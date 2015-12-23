package com.ulplanet.trip.bean;

import java.util.Date;

public class JourneyPlan  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String dayId;
	private String infoId;
	private Integer type;
	private String name;
	private Date time;
	private Integer timeFlag;
	private String description;
	private String message;
	private Integer messageFlag;
	private Integer sort;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String remark;
	private String typeValue;
	private String longitude;
	private String latitude;
	private Integer feedbackFlag;

	public Integer getFeedbackFlag() {
		return feedbackFlag;
	}

	public void setFeedbackFlag(Integer feedbackFlag) {
		this.feedbackFlag = feedbackFlag;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public JourneyPlan(){
	}

	public JourneyPlan(
		String id
	){
		this.id = id;
	}


	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	public void setDayId(String value) {
		this.dayId = value;
	}
	
	public String getDayId() {
		return this.dayId;
	}

	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}

	public void setTime(java.util.Date value) {
		this.time = value;
	}
	
	public java.util.Date getTime() {
		return this.time;
	}

	public void setTimeFlag(Integer value) {
		this.timeFlag = value;
	}
	
	public Integer getTimeFlag() {
		return this.timeFlag;
	}

	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setMessage(String value) {
		this.message = value;
	}
	
	public String getMessage() {
		return this.message;
	}

	public void setMessageFlag(Integer value) {
		this.messageFlag = value;
	}
	
	public Integer getMessageFlag() {
		return this.messageFlag;
	}

	public void setSort(Integer value) {
		this.sort = value;
	}
	
	public Integer getSort() {
		return this.sort;
	}

	public void setCreateBy(String value) {
		this.createBy = value;
	}
	
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public void setUpdateBy(String value) {
		this.updateBy = value;
	}
	
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateDate(Date value) {
		this.updateDate = value;
	}
	
	public java.util.Date getUpdateDate() {
		return new Date(updateDate.getTime());
	}

	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
}

