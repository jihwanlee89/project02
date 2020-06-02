package com.springboot.payment.model;

import java.util.Date;

public class CardPayModel {

	public String uniqueId;
	public String status;
	public int amount;
	public int tax;
	public String encCardNumber;
	public String encCardExpire;
	public String encCardCvc;
	public int cardQuota;
	public String transactionId;
	public Date applDate;
	public Date cancelDate;
	public String cancelUniqueId;

	public String cardNumber;
	public String cardExpire;
	public String cardCvc;

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelUniqueId() {
		return cancelUniqueId;
	}

	public void setCancelUniqueId(String cancelUniqueId) {
		this.cancelUniqueId = cancelUniqueId;
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

	public Date getApplDate() {
		return applDate;
	}

	public void setApplDate(Date applDate) {
		this.applDate = applDate;
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

	public String getEncCardNumber() {
		return encCardNumber;
	}

	public void setEncCardNumber(String encCardNumber) {
		this.encCardNumber = encCardNumber;
	}

	public String getEncCardExpire() {
		return encCardExpire;
	}

	public void setEncCardExpire(String encCardExpire) {
		this.encCardExpire = encCardExpire;
	}

	public String getEncCardCvc() {
		return encCardCvc;
	}

	public void setEncCardCvc(String encCardCvc) {
		this.encCardCvc = encCardCvc;
	}

	public int getCardQuota() {
		return cardQuota;
	}

	public void setCardQuota(int cardQuota) {
		this.cardQuota = cardQuota;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
