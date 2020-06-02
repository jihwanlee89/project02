package com.springboot.payment.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.payment.component.CardInitialize;
import com.springboot.payment.model.CardPayModel;
import com.springboot.payment.requestVo.payCancelVo.PayCancelRequestVo;
import com.springboot.payment.requestVo.payExecuteVo.CardPayRequestVo;
import com.springboot.payment.requestVo.payInquiryVo.PayInquiryRequestVo;
import com.springboot.payment.responseVo.payCancelVo.CancelResponseVo;
import com.springboot.payment.responseVo.payExcuteVo.CardPayResponseVo;
import com.springboot.payment.responseVo.payInquiryVo.PayInquiryResponseVo;
import com.springboot.payment.service.PaymentService;

@RestController
@RequestMapping("/payment/v1")
public class PayController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CardInitialize cardInitialize;

	@Autowired
	PaymentService paymentService;

	ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(method = RequestMethod.POST, value = "/pay")
	public CardPayResponseVo cardPay(@RequestBody @Valid CardPayRequestVo cardPayRequestVo,
			CardPayResponseVo cardPayResponse) throws Exception {

		logger.info("cardPay Initialize Start");

		cardInitialize.requestPayInitialize(cardPayRequestVo);

		logger.info("Initailize : " + cardPayRequestVo.toString());
		logger.info("cardPay Initialize End");

		CardPayModel cardPayModel = paymentService.cardPayProcess(cardPayRequestVo);

		cardPayResponse.setAmount(cardPayModel.getAmount());
		cardPayResponse.setTax(cardPayModel.getTax());
		cardPayResponse.setUniqueId(cardPayModel.getUniqueId());
		cardPayResponse.setStatus(cardPayModel.getStatus());
		cardPayResponse.setApplDate(cardPayModel.getApplDate());

		return cardPayResponse;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pay")
	public PayInquiryResponseVo payInquiry(@Valid PayInquiryRequestVo payInquiryRequestVo,
			PayInquiryResponseVo payInquiryResponseVo) throws Exception {

		CardPayModel cardPayModel = paymentService.payInquiry(payInquiryRequestVo);

		payInquiryResponseVo = objectMapper.readValue(objectMapper.writeValueAsString(cardPayModel),
				PayInquiryResponseVo.class);

		return payInquiryResponseVo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/pay/cancel")
	public CancelResponseVo payCancel(@RequestBody @Valid PayCancelRequestVo payCancelRequestVo,CancelResponseVo cancelResponseVo) throws Exception {

		logger.info("cancel Initialize Start");

		cardInitialize.requestCancelInitialize(payCancelRequestVo);

		logger.info("Initailize : " + payCancelRequestVo.toString());
		logger.info("cancel Initialize End");

		CardPayModel cardPayModel = paymentService.cancelProcess(payCancelRequestVo);

		cancelResponseVo = objectMapper.readValue(objectMapper.writeValueAsString(cardPayModel),
				CancelResponseVo.class);

		return cancelResponseVo;
	}

}
