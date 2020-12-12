package com.payment;

public class PaymentDetails {
	String name;
	float amount;
	String paid_by;
	String split_by;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getPaid_by() {
		return paid_by;
	}
	public void setPaid_by(String paid_by) {
		this.paid_by = paid_by;
	}
	public String getSplit_by() {
		return split_by;
	}
	public void setSplit_by(String split_by) {
		this.split_by = split_by;
	}
	
	

}
