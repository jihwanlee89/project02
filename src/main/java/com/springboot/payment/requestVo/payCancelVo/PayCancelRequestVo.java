package com.springboot.payment.requestVo.payCancelVo;

import javax.validation.constraints.NotEmpty;

import com.springboot.payment.requestVo.CommonRequestVo;

public class PayCancelRequestVo extends CommonRequestVo {

	@NotEmpty(message = "전체 취소는 거래 uniqueId가 필수 입니다. ")
	public String uniqueId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
