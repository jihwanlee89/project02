package com.springboot.payment.requestVo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CommonRequestVo {

	@Min(value = 100, message = "100원 이상, 10억원 이하만 가능합니다.")
	@Max(value = 1000000000, message = "100원 이상, 10억원 이하만 가능합니다.")
	@NotNull(message = "결제 및 취소 금액은 필수 입니다.")
	public Integer amount;

	@Min(value = 0, message = "tax 는 0원 이상 결제금액 이하 여야 합니다.")
	public Integer tax;
	public String transaction_id;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getTax() {
		return tax;
	}

	public void setTax(Integer tax) {

		this.tax = tax;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

}
