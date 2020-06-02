package com.springboot.payment.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.springboot.payment.exception.code.ErrorCode;

@JsonInclude(Include.NON_NULL)
public class PaymentErrorResponse {

	public String resultCode;
	public String resultMessage;

	public PaymentErrorResponse of(String message) {
		PaymentErrorResponse paymentErrorResponse = new PaymentErrorResponse();
		paymentErrorResponse.resultMessage = message;

		return paymentErrorResponse;
	}

	public PaymentErrorResponse of(ErrorCode errorCode) {
		PaymentErrorResponse paymentErrorResponse = new PaymentErrorResponse();
		paymentErrorResponse.resultCode = errorCode.getResultCode();
		paymentErrorResponse.resultMessage = errorCode.getResultMessage();

		return paymentErrorResponse;
	}

	public PaymentErrorResponse of(ErrorCode errorCode, String message) {
		PaymentErrorResponse paymentErrorResponse = new PaymentErrorResponse();
		paymentErrorResponse.resultCode = errorCode.getResultCode();
		paymentErrorResponse.resultMessage = message;

		return paymentErrorResponse;
	}

}
