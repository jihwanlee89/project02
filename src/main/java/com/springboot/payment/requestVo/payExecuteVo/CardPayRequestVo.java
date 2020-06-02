package com.springboot.payment.requestVo.payExecuteVo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.springboot.payment.requestVo.CommonRequestVo;

public class CardPayRequestVo extends CommonRequestVo {

	@Size(min = 10, max = 20, message = "카드번호는 최소 10 자리 최대 20 자리 입니다.")
	@Pattern(regexp = "^[0-9]+$", message = "카드번호는 숫자만 가능합니다.")
	@NotEmpty(message = "카드 번호는 필수 값 입니다.")
	public String card_number;

	@Pattern(regexp = "^[0-9]+$", message = "카드유효기간은 숫자만 가능합니다.")
	@Size(min = 4, max = 4, message = "카드유효기간은 4자리입니다.")
	@NotEmpty(message = "카드 유효기간은 필수 값 입니다.")
	public String card_exprie;

	@Pattern(regexp = "^[0-9]+$", message = "카드CVC는 숫자만 가능합니다.")
	@Size(min = 3, max = 3, message = "카드CVC는 3자리입니다.")
	@NotEmpty(message = "카드 CVC는 필수 값 입니다.")
	public String card_cvc;

	@Min(value = 0, message = "할부기간은 0 ~ 12 개월만 가능합니다.")
	@Max(value = 12, message = "할부기간은 0 ~ 12 개월만 가능합니다.")
	@NotNull(message = "할부기간은 필수 값 입니다.")
	public Integer card_quota;

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getCard_exprie() {
		return card_exprie;
	}

	public void setCard_exprie(String card_exprie) {
		this.card_exprie = card_exprie;
	}

	public String getCard_cvc() {
		return card_cvc;
	}

	public void setCard_cvc(String card_cvc) {
		this.card_cvc = card_cvc;
	}

	public Integer getCard_quota() {
		return card_quota;
	}

	public void setCard_quota(Integer card_quota) {
		this.card_quota = card_quota;
	}

	@Override
	public String toString() {
		return " amount [" + this.amount + "], tax [" + this.tax + "], card_number [" + this.card_number
				+ "], card_exprie [" + this.card_exprie + "], card_cvc [" + this.card_cvc + "], card_quota ["
				+ this.card_quota + "]";
	}
}
