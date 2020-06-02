package com.springboot.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.payment.model.CancelAbleAmountModel;
import com.springboot.payment.model.CardPayModel;

@Mapper
public interface PaymentMapper {
	
	//카드사 요청 전 원 거래 TABLE DATA INSERT
	boolean cardPayProcessDataInsert(CardPayModel cardPayModel);
	
	//카드사 요청 응답 완료 후 원거래 TABLE DATA UPDATE
	boolean cardPayProcessPayUpdate(CardPayModel cardPayModel);
	
	//원 거래 DATA 조회
	CardPayModel payInquiry(CardPayModel cardPayModel);

	//취소가능 금액 조회
	CancelAbleAmountModel cancelAbleAmountSelect(CardPayModel cardPayModel);

	//카드사 요청 전 취소 테이블 취소 DATA INSERT
	boolean cardCancelProcessDataInsert(CardPayModel cardPayModel);
	
	//카드사 요청 완료 후 취소 테이블 취소 DATA UPDATE
	boolean cardCancelProcessDataUpdate(CardPayModel cardPayModel);
	
	//취소시 원거래 TABLE UPDATE
	boolean cardPayProcessCancelUpdate(CardPayModel cardPayModel);
}
