package com.springboot.payment.requestVo.payExecuteVo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.springboot.payment.requestVo.CommonRequestVo;

public class CardPayRequestVo extends CommonRequestVo {

	@Min(value = 100, message = "100원 이상, 10억원 이하만 가능합니다.")
	@Max(value = 1000000000, message = "100원 이상, 10억원 이하만 가능합니다.")
	@NotNull(message = "결제 및 취소 금액은 필수 입니다.")
	public Integer amount;

	@Size(min = 10, max = 20, message = "카드번호는 최소 10 자리 최대 20 자리 입니다.")
	@Pattern(regexp = "^[0-9]+$", message = "카드번호는 숫자만 가능합니다.")
	@NotEmpty(message = "카드 번호는 필수 값 입니다.")
	public String cardNumber;

	@Pattern(regexp = "^[0-9]+$", message = "카드유효기간은 숫자만 가능합니다.")
	@Size(min = 4, max = 4, message = "카드유효기간은 4자리입니다.")
	@NotEmpty(message = "카드 유효기간은 필수 값 입니다.")
	public String cardExpire;

	@Pattern(regexp = "^[0-9]+$", message = "카드CVC는 숫자만 가능합니다.")
	@Size(min = 3, max = 3, message = "카드CVC는 3자리입니다.")
	@NotEmpty(message = "카드 CVC는 필수 값 입니다.")
	public String cardCvc;

	@Min(value = 0, message = "할부기간은 0 ~ 12 개월만 가능합니다.")
	@Max(value = 12, message = "할부기간은 0 ~ 12 개월만 가능합니다.")
	@NotNull(message = "할부기간은 필수 값 입니다.")
	public Integer cardQuota;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpire() {
		return cardExpire;
	}

	public void setCardExpire(String cardExpire) {
		this.cardExpire = cardExpire;
	}

	public String getCardCvc() {
		return cardCvc;
	}

	public void setCardCvc(String cardCvc) {
		this.cardCvc = cardCvc;
	}

	public Integer getCardQuota() {
		return cardQuota;
	}

	public void setCardQuota(Integer cardQuota) {
		this.cardQuota = cardQuota;
	}

	@Override
	public String toString() {
		return " amount [" + this.amount + "], tax [" + this.tax + "], cardNumber [" + this.cardNumber
				+ "], cardExpire [" + this.cardExpire + "], cardCvc [" + this.cardCvc + "], cardQuota ["
				+ this.cardQuota + "]";
	}
}
