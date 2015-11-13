package com.ulplanet.trip.bean;


import com.ulplanet.trip.common.persistence.DataEntity;

public class RepairPhone  extends DataEntity<RepairPhone> implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	private String phoneId;
	private String reason;
	private String repairTime;
	private String endTime;
	private String comment;
	private int status;

	public RepairPhone(){
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public RepairPhone(
		String id
	){
		this.id = id;
	}



	public void setPhoneId(String value) {
		this.phoneId = value;
	}
	
	public String getPhoneId() {
		return this.phoneId;
	}

	public void setReason(String value) {
		this.reason = value;
	}
	
	public String getReason() {
		return this.reason;
	}

	public String getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(String repairTime) {
		this.repairTime = repairTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setComment(String value) {
		this.comment = value;
	}
	
	public String getComment() {
		return this.comment;
	}




	

	

}

