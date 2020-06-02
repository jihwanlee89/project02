package com.springboot.payment.requestVo.payInquiryVo;

import javax.validation.constraints.NotEmpty;

public class PayInquiryRequestVo {
	
	@NotEmpty(message = "거래 조회를 위해서는 uniqueId 는 필수 입니다.")
	public String uniqueId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
