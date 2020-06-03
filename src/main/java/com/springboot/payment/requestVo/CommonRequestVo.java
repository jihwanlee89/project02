package com.springboot.payment.requestVo;

import javax.validation.constraints.Min;

public class CommonRequestVo {

	@Min(value = 0, message = "tax 는 0원 이상 결제금액 이하 여야 합니다.")
	public Integer tax;
	public String transactionId;

	public Integer getTax() {
		return tax;
	}

	public void setTax(Integer tax) {

		this.tax = tax;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
