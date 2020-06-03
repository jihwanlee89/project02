package com.springboot.payment.responseVo.payInquiryVo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayInquiryResponseVo {

	public String uniqueId;
	public String status;
	public int amount;
	public int tax;
	public int cancelAmount;
	public int cancelTax;
	public String cardNumber;
	public String cardExpire;
	public String cardCvc;
	public int cardQuota;
	public Date applDate;
	public Date cancelDate;

	public int getCancelAmount() {
		return cancelAmount;
	}

	public void setCancelAmount(int cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public int getCancelTax() {
		return cancelTax;
	}

	public void setCancelTax(int cancelTax) {
		this.cancelTax = cancelTax;
	}

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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpire() {
		return cardExpire;
	}

	public void setCardExpire(String cardExpire) {
		this.cardExpire = cardExpire;
	}

	public String getCardCvc() {
		return cardCvc;
	}

	public void setCardCvc(String cardCvc) {
		this.cardCvc = cardCvc;
	}

	public int getCardQuota() {
		return cardQuota;
	}

	public void setCardQuota(int cardQuota) {
		this.cardQuota = cardQuota;
	}

	public String getApplDate() {
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return applDate != null ? simpleDate.format(applDate) : null;
	}

	public void setApplDate(Date applDate) {
		this.applDate = applDate;
	}

	public String getCancelDate() {
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return cancelDate != null ? simpleDate.format(cancelDate) : null;

	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

}
