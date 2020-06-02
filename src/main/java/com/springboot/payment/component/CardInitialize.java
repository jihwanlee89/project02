package com.springboot.payment.component;

import org.springframework.stereotype.Component;

import com.springboot.payment.exception.code.ErrorCode;
import com.springboot.payment.exception.customException.CardpayInitializeException;
import com.springboot.payment.requestVo.CommonRequestVo;

@Component
public class CardInitialize {

	public void requestPayInitialize(CommonRequestVo commonRequestVo) throws Exception {

		int amount = commonRequestVo.getAmount();

		if (commonRequestVo.getTax() == null) {
			int tax = (int) Math.round((double) amount / (double) 11);
			commonRequestVo.setTax(tax);

		} else if (amount < (int) commonRequestVo.getTax()) {
			throw new CardpayInitializeException(ErrorCode.ER001, "tax 는 결제 금액 보다 클 수 없습니다.");
		}

	}
	
	public void requestCancelInitialize(CommonRequestVo commonRequestVo) throws Exception {

		int amount = commonRequestVo.getAmount();

		if (commonRequestVo.getTax() == null) {

		} else if (amount < (int) commonRequestVo.getTax()) {
			throw new CardpayInitializeException(ErrorCode.ER001, "tax 는 결제 금액 보다 클 수 없습니다.");
		}

	}
}
