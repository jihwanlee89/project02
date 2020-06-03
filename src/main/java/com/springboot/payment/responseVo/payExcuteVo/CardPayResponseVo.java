package com.springboot.payment.responseVo.payExcuteVo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CardPayResponseVo extends SuccessPayResponseVo {

	public int amount;
	public int tax;
	public String uniqueId;
	public String status;
	public Date applDate;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplDate() {

		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return applDate != null ? simpleDate.format(applDate) : null;
	}

	public void setApplDate(Date applDate) {
		this.applDate = applDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

}
