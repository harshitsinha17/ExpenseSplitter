package com.participant;

public class Users {
	
	String name;
	@Override
	public String toString() {
		return "Users [name=" + name + ", debtAmount=" + debtAmount + ", collectAmountFrom=" + collectAmountFrom + "]";
	}
	float debtAmount;
	String collectAmountFrom;
	
	public Users(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getAmount() {
		return debtAmount;
	}
	public void setAmount(float amount) {
		this.debtAmount = amount;
	}
	public String getCollectAmountFrom() {
		return collectAmountFrom;
	}
	public void setCollectAmountFrom(String collectAmountFrom) {
		this.collectAmountFrom = collectAmountFrom;
	}

}
