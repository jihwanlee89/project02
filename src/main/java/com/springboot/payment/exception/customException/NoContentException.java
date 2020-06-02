package com.springboot.payment.exception.customException;

import com.springboot.payment.exception.code.ErrorCode;

public class NoContentException extends Exception {

	private static final long serialVersionUID = -1511413411144197517L;
	public ErrorCode errorCdoe;

	public NoContentException() {
		super();
	}

	public NoContentException(ErrorCode errorCdoe) {
		super();
		this.errorCdoe = errorCdoe;
	}

	public NoContentException(ErrorCode errorCdoe, String message) {
		super(message);
		this.errorCdoe = errorCdoe;
	}
}
