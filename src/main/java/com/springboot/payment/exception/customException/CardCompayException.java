package com.springboot.payment.exception.customException;

import com.springboot.payment.exception.code.ErrorCode;

public class CardCompayException extends Exception {

	private static final long serialVersionUID = -9106950485256404894L;
	public ErrorCode errorCdoe;

	public CardCompayException() {
		super();
	}

	public CardCompayException(ErrorCode errorCdoe) {
		super();
		this.errorCdoe = errorCdoe;
	}

	public CardCompayException(ErrorCode errorCdoe, String message) {
		super(message);
		this.errorCdoe = errorCdoe;
	}
}
