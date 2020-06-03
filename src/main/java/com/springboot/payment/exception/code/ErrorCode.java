package com.springboot.payment.exception.code;

public enum ErrorCode {

	ER001("ER001", "요청 파라미터가 올바르지 않습니다."),
	ER002("ER002", "내부 시스템에 에러가 있습니다. 담당자에게 확인 부탁드립니다."),
	ER003("ER003", "카드사 결제 실패."),
	ER004("ER004", "남아 있는 승인 금액 보다 취소 요청 금액이 큽니다."),
	ER005("ER005", "취소부가세가 원 거래부가세 보다 큽니다."),
	ER006("ER006", "해당 원 거래가 존재 하지 않습니다."),
	ER007("ER007", "남이 있는 부가기치세를 정확히 요청 하여주세요."),
	ER008("ER008", "해당 카드로 진행중인 거래가 있습니다. 완료 후 거래 진행 하여 주세요."),
	ER009("ER009", "해당 고유번호로 진행중인 취소가 있습니다. 완료 후 취소 진행 하여 주세요."),
	;
	
	private String resultCode;
	private String resultMessage;

	private ErrorCode(String resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

}
