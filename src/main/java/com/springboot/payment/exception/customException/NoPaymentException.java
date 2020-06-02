package com.springboot.payment.exception.customException;

import com.springboot.payment.exception.code.ErrorCode;

public class NoPaymentException extends Exception {

	private static final long serialVersionUID = -6482149559612418434L;
	public ErrorCode errorCdoe;

	public NoPaymentException() {
		super();
	}

	public NoPaymentException(ErrorCode errorCdoe) {
		super();
		this.errorCdoe = errorCdoe;
	}

	public NoPaymentException(ErrorCode errorCdoe, String message) {
		super(message);
		this.errorCdoe = errorCdoe;
	}
}
