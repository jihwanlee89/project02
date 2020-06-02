package com.springboot.payment.exception.customException;

import com.springboot.payment.exception.code.ErrorCode;

public class DataBaseException extends Exception {

	private static final long serialVersionUID = -2013126198214930568L;
	public ErrorCode errorCdoe;

	public DataBaseException() {
		super();
	}

	public DataBaseException(ErrorCode errorCdoe) {
		super();
		this.errorCdoe = errorCdoe;
	}

	public DataBaseException(ErrorCode errorCdoe, String message) {
		super(message);
		this.errorCdoe = errorCdoe;
	}
}
