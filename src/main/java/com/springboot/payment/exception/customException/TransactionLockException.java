package com.springboot.payment.exception.customException;

import com.springboot.payment.exception.code.ErrorCode;

public class TransactionLockException extends Exception {

	private static final long serialVersionUID = -2458050475183871294L;
	public ErrorCode errorCdoe;

	public TransactionLockException(ErrorCode errorCdoe) {
		super();
		this.errorCdoe = errorCdoe;
	}

	public TransactionLockException(ErrorCode errorCdoe, String message) {
		super(message);
		this.errorCdoe = errorCdoe;
	}
}
