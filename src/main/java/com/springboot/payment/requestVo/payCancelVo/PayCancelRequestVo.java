package com.springboot.payment.requestVo.payCancelVo;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.springboot.payment.requestVo.CommonRequestVo;

public class PayCancelRequestVo extends CommonRequestVo {

	@Max(value = 1000000000, message = "10억원 이하만 가능합니다.")
	@NotNull(message = "결제 및 취소 금액은 필수 입니다.")
	public Integer amount;

	@NotEmpty(message = "전체 취소는 거래 uniqueId가 필수 입니다. ")
	public String uniqueId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
