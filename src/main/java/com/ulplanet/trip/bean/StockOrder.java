package com.ulplanet.trip.bean;


import com.ulplanet.trip.common.persistence.DataEntity;

public class StockOrder  extends DataEntity<StockOrder> implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String orderId;
	private Double unitPrice;
	private Integer quantity;
	private Double totalPrice;
	private String insurance;
	private String model;
	private String buyer;
	private String buyingTime;
	private String comment;
	private Integer status;
	private String email;

	public StockOrder(){
	}

	public StockOrder(
		String id
	){
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public void setOrderId(String value) {
		this.orderId = value;
	}
	
	public String getOrderId() {
		return this.orderId;
	}


	public void setQuantity(Integer value) {
		this.quantity = value;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public void setModel(String value) {
		this.model = value;
	}
	
	public String getModel() {
		return this.model;
	}

	public void setBuyer(String value) {
		this.buyer = value;
	}
	
	public String getBuyer() {
		return this.buyer;
	}

	public String getBuyingTime() {
		return buyingTime;
	}

	public void setBuyingTime(String buyingTime) {
		this.buyingTime = buyingTime;
	}

	public void setComment(String value) {
		this.comment = value;
	}
	
	public String getComment() {
		return this.comment;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

