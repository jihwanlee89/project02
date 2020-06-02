package com.springboot.payment.exception.customException;

import com.springboot.payment.exception.code.ErrorCode;

public class CardpayInitializeException extends Exception {

	private static final long serialVersionUID = 1L;
	public ErrorCode errorCdoe;

	public CardpayInitializeException(ErrorCode errorCdoe) {
		super();
		this.errorCdoe = errorCdoe;
	}

	public CardpayInitializeException(ErrorCode errorCdoe, String message) {
		super(message);
		this.errorCdoe = errorCdoe;
	}
}
