package com.springboot.payment.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.payment.exception.code.ErrorCode;
import com.springboot.payment.exception.customException.CancelAmountException;
import com.springboot.payment.exception.customException.CardCompayException;
import com.springboot.payment.exception.customException.CardpayInitializeException;
import com.springboot.payment.exception.customException.DataBaseException;
import com.springboot.payment.exception.customException.NoContentException;
import com.springboot.payment.exception.customException.NoPaymentException;
import com.springboot.payment.exception.customException.TransactionLockException;

@RestControllerAdvice
public class PaymentExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	PaymentErrorResponse paymentErrorResponse = new PaymentErrorResponse();

	@ExceptionHandler(BindException.class)
	public ResponseEntity<PaymentErrorResponse> handleBindException(BindException e) {
		FieldError fieldError = e.getBindingResult().getFieldError();

		logger.warn("BAD_REQUEST : {} / {}", fieldError.getCode(), fieldError.getDefaultMessage());

		return new ResponseEntity<PaymentErrorResponse>(
				paymentErrorResponse.of(ErrorCode.ER010, fieldError.getDefaultMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<PaymentErrorResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		FieldError fieldError = e.getBindingResult().getFieldError();

		logger.warn("BAD_REQUEST : {} / {}", fieldError.getCode(), fieldError.getDefaultMessage());

		return new ResponseEntity<PaymentErrorResponse>(
				paymentErrorResponse.of(ErrorCode.ER001, fieldError.getDefaultMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<PaymentErrorResponse> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException e) {

		logger.warn("BAD_REQUEST : {}", e.getMessage());

		return new ResponseEntity<>(paymentErrorResponse.of(ErrorCode.ER001, "올바른 JSON DATA 형태 및 변수형이 아닙니다."),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<PaymentErrorResponse> handleHttpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException e) {

		logger.warn("UNSUPPORTED_MEDIA_TYPE : {}", e.getMessage());

		return new ResponseEntity<>(paymentErrorResponse.of(ErrorCode.ER001, "지원하지 않는 ContentType 입니다."),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler(CardpayInitializeException.class)
	public ResponseEntity<PaymentErrorResponse> handleCardpayInitializeException(CardpayInitializeException e) {

		logger.warn("CARDPAYINITIALIZE EXEPTION : {}", e.getMessage());

		return new ResponseEntity<>(paymentErrorResponse.of(ErrorCode.ER001, e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<PaymentErrorResponse> handleDataBaseException(DataBaseException e) {

		logger.warn("DATABASE EXEPTION : {}", e.getMessage());

		return new ResponseEntity<>(paymentErrorResponse.of(ErrorCode.ER002), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CardCompayException.class)
	public ResponseEntity<PaymentErrorResponse> handleCardCompayException(CardCompayException e) {

		logger.warn("CARDCAOMPAY EXEPTION : {}", e.getMessage());

		return new ResponseEntity<>(paymentErrorResponse.of(ErrorCode.ER003), HttpStatus.OK);
	}

	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<PaymentErrorResponse> handleNoContentException(NoContentException e) {

		logger.warn("NOCONTENT EXEPTION : {}", e.getMessage());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(CancelAmountException.class)
	public ResponseEntity<PaymentErrorResponse> handleCancelAmountException(CancelAmountException e) {

		logger.warn("CANCELAMOUNT EXEPTION : {}", e.getMessage());

		return new ResponseEntity<>(paymentErrorResponse.of(e.errorCdoe), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoPaymentException.class)
	public ResponseEntity<PaymentErrorResponse> handleNoPaymentException(NoPaymentException e) {

		logger.warn("NOPAYMETN EXEPTION : {}", e.getMessage());

		return new ResponseEntity<>(paymentErrorResponse.of(e.errorCdoe), HttpStatus.OK);
	}

	@ExceptionHandler(TransactionLockException.class)
	public ResponseEntity<PaymentErrorResponse> handleTransactionException(TransactionLockException e) {

		logger.warn("TRANSACTION EXEPTION : {}", e.getMessage());

		return new ResponseEntity<>(paymentErrorResponse.of(e.errorCdoe), HttpStatus.OK);
	}
}
