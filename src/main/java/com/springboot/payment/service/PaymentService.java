package com.springboot.payment.service;

import com.springboot.payment.model.CardPayModel;
import com.springboot.payment.requestVo.payCancelVo.PayCancelRequestVo;
import com.springboot.payment.requestVo.payExecuteVo.CardPayRequestVo;
import com.springboot.payment.requestVo.payInquiryVo.PayInquiryRequestVo;

public interface PaymentService {

	public CardPayModel cardPayProcess(CardPayRequestVo cardPayRequestVo) throws Exception;

	public CardPayModel payInquiry(PayInquiryRequestVo payInquiryRequestVo) throws Exception;

	public CardPayModel cancelProcess(PayCancelRequestVo payCancelRequestVo) throws Exception;

}
