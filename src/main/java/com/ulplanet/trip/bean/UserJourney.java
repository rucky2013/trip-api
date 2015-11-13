package com.ulplanet.trip.bean;




public class UserJourney implements java.io.Serializable{

	private static final long serialVersionUID = 5454155825314635342L;
	private String id;
	private String userCode;
	private Integer active;
	private String journeyId;
	private String createBy;
	private java.util.Date createDate;
	private String updateBy;
	private java.util.Date updateDate;
	private String remark;

	public UserJourney(){
	}

	public UserJourney(
			String id
	){
		this.id = id;
	}


	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public void setActive(Integer value) {
		this.active = value;
	}
	
	public Integer getActive() {
		return this.active;
	}

	public void setJourneyId(String value) {
		this.journeyId = value;
	}
	
	public String getJourneyId() {
		return this.journeyId;
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

	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}
	
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}



	

	

}

