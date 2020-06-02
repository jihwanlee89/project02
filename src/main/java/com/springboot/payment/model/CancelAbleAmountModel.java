package com.springboot.payment.model;

public class CancelAbleAmountModel {

	public int cancelAbleAmount;
	public int cancelAbleTax;

	public int getCancelAbleAmount() {
		return cancelAbleAmount;
	}

	public void setCancelAbleAmount(int cancelAbleAmount) {
		this.cancelAbleAmount = cancelAbleAmount;
	}

	public int getCancelAbleTax() {
		return cancelAbleTax;
	}

	public void setCancelAbleTax(int cancelAbleTax) {
		this.cancelAbleTax = cancelAbleTax;
	}

}
