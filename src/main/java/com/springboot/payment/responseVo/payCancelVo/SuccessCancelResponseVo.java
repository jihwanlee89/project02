package com.springboot.payment.responseVo.payCancelVo;

public class SuccessCancelResponseVo {

	public String resultCode = "R000";
	public String resultMessage = "SUCCESS";

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

}
