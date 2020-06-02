package com.springboot.payment.exception.customException;

import com.springboot.payment.exception.code.ErrorCode;

public class CancelAmountException extends Exception {

	private static final long serialVersionUID = -1511413411144197517L;
	public ErrorCode errorCdoe;

	public CancelAmountException() {
		super();
	}

	public CancelAmountException(ErrorCode errorCdoe) {
		super();
		this.errorCdoe = errorCdoe;
	}

	public CancelAmountException(ErrorCode errorCdoe, String message) {
		super(message);
		this.errorCdoe = errorCdoe;
	}
}
