package com.springboot.payment.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.payment.component.GenerateIdTool;
import com.springboot.payment.exception.code.ErrorCode;
import com.springboot.payment.exception.customException.CancelAmountException;
import com.springboot.payment.exception.customException.CardCompayException;
import com.springboot.payment.exception.customException.DataBaseException;
import com.springboot.payment.exception.customException.NoContentException;
import com.springboot.payment.exception.customException.NoPaymentException;
import com.springboot.payment.mapper.CardCompanyMapper;
import com.springboot.payment.mapper.PaymentMapper;
import com.springboot.payment.model.CancelAbleAmountModel;
import com.springboot.payment.model.CardPayModel;
import com.springboot.payment.requestVo.payCancelVo.PayCancelRequestVo;
import com.springboot.payment.requestVo.payExecuteVo.CardPayRequestVo;
import com.springboot.payment.requestVo.payInquiryVo.PayInquiryRequestVo;
import com.springboot.payment.service.PaymentService;
import com.springboot.util.CardCompayRequestData;
import com.springboot.util.CommonUtil;
import com.springboot.util.CryptoUtil;

@Service
public class PaymentServiceImpl implements PaymentService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PaymentMapper paymentMapper;

	@Autowired
	CardCompanyMapper cardCompanyMapper;

	@Override
	public CardPayModel cardPayProcess(CardPayRequestVo cardPayRequestVo) throws Exception {

		CardPayModel cardPayModel = new CardPayModel();
		cardPayModel.setUniqueId(GenerateIdTool.generatePayUniqueId());
		cardPayModel.setAmount(cardPayRequestVo.getAmount());
		cardPayModel.setTax(cardPayRequestVo.tax);
		cardPayModel.setEncCardNumber(CryptoUtil.seedCbcEncrypt(cardPayRequestVo.getCardNumber()));
		cardPayModel.setEncCardExpire(CryptoUtil.seedCbcEncrypt(cardPayRequestVo.getCardExpire()));
		cardPayModel.setEncCardCvc(CryptoUtil.seedCbcEncrypt(cardPayRequestVo.getCardCvc()));
		cardPayModel.setCardQuota(cardPayRequestVo.getCardQuota());
		cardPayModel.setTransactionId(cardPayRequestVo.transactionId);
		cardPayModel.setStatus("READY");

		/* DATA DB 저장 START */
		logger.info("DATA DB 원장 저장 START");
		try {
			if (!paymentMapper.cardPayProcessDataInsert(cardPayModel)) {
				throw new DataBaseException();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new DataBaseException();
		}
		logger.info("DATA DB 원장 저장 END");
		/* DATA DB 저장 END */

		/* 카드사 전문 생성 START */
		logger.info("카드사 전문 생성 START");
		String requestData = CardCompayRequestData.generateCardPayData(cardPayModel);
		/* 카드사 전문 생성 END */
		logger.info("카드사 전문 생성 END");

		/* 카드사 통신 START */
		logger.info("카드사 통신 START");
		try {
			logger.info("REQUEST DATA : " + requestData);

			if (cardCompanyMapper.insertRequestData(requestData)) {
				// 승인 STATUS SETTING
				cardPayModel.setStatus("PAY");
				// 승인 TIMESTAMP 셋팅
				cardPayModel.setApplDate(new Date());
				if (!paymentMapper.cardPayProcessPayUpdate(cardPayModel)) {
					throw new DataBaseException();
				}
			} else {
				throw new CardCompayException();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new CardCompayException();
		}
		logger.info("카드사 통신 END");
		/* 카드사 통신 END */

		return cardPayModel;
	}

	@Override
	public CardPayModel payInquiry(PayInquiryRequestVo payInquiryRequestVo) throws Exception {

		CardPayModel cardPayModel = new CardPayModel();
		cardPayModel.setUniqueId(payInquiryRequestVo.getUniqueId());

		cardPayModel = paymentMapper.payInquiry(cardPayModel);

		if (cardPayModel == null) {
			throw new NoContentException();
		}

		cardPayModel.setCardNumber(
				CommonUtil.cardNumberMasking(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardNumber())));
		cardPayModel.setCardExpire(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardExpire()));
		cardPayModel.setCardCvc(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardCvc()));

		return cardPayModel;
	}

	@Override
	public CardPayModel cancelProcess(PayCancelRequestVo payCancelRequestVo) throws Exception {

		int cancleAbleAmount = 0; // 취소가능금액
		int cancelAbleTax = 0; // 취소가능부가세
		int cancelRequestAmount = 0; // 취소요청금액
		int cancelRequestTax = 0; // 취소요청부가세

		CardPayModel cardPayModel = new CardPayModel();
		cardPayModel.setUniqueId(payCancelRequestVo.getUniqueId());

		// 취소 가능 금액 조회
		CancelAbleAmountModel cancelAbleAmountModel = paymentMapper.cancelAbleAmountSelect(cardPayModel);

		if (cancelAbleAmountModel == null) {
			throw new NoPaymentException(ErrorCode.ER006);
		}
		cancleAbleAmount = cancelAbleAmountModel.getCancelAbleAmount();
		cancelAbleTax = cancelAbleAmountModel.getCancelAbleTax();
		cancelRequestAmount = payCancelRequestVo.getAmount();
		if (payCancelRequestVo.getTax() != null) {
			cancelRequestTax = payCancelRequestVo.getTax();
		}

		logger.info("취소가능 금액  : " + cancleAbleAmount);
		logger.info("취소요청 금액 : " + cancelRequestAmount);
		logger.info("취소가능 부가세  : " + cancelAbleTax);
		logger.info("취소요청 부가세 : " + cancelRequestTax);

		// 전체취소 일 경우
		if (cancleAbleAmount == cancelRequestAmount) {

			// TAX 가 null 이고 전체 취소 일 경우 남아있는 부가세를 취소한다.
			if (payCancelRequestVo.getTax() == null) {
				cancelRequestTax = cancelAbleTax;
			} else if (cancelAbleTax != cancelRequestTax) {
				throw new CancelAmountException(ErrorCode.ER007);
			}

			cardPayModel = paymentMapper.payInquiry(cardPayModel);
			cardPayModel.setStatus("READY");
			cardPayModel.setCancelUniqueId(GenerateIdTool.generateCancelUniqueId());
			cardPayModel.setTransactionId(payCancelRequestVo.getTransactionId());
			cardPayModel.setAmount(cancelRequestAmount);
			// 전체 취소시 남아있는 부가세를 전부 취소한다.
			cardPayModel.setTax(cancelRequestTax);
			try {
				paymentMapper.cardCancelProcessDataInsert(cardPayModel);
			} catch (Exception e) {
				e.printStackTrace();
				throw new DataBaseException();
			}

			logger.info("카드사 취소 전문 생성 START");
			String requestData = CardCompayRequestData.generateCancelData(cardPayModel);
			/* 카드사 전문 생성 END */
			logger.info("카드사 취소 전문 생성 END");

			/* 카드사 통신 START */
			logger.info("카드사 취소 통신 START");
			try {
				logger.info("REQUEST DATA : " + requestData);

				// 카드사 저장
				if (cardCompanyMapper.insertRequestData(requestData)) {
					// 취소 STATUS SETTING
					cardPayModel.setStatus("CANCEL");
					// 취소 TIMESTAMP 셋팅
					cardPayModel.setCancelDate(new Date());

					// 카드 취소 TABLE UPDATE
					if (!paymentMapper.cardCancelProcessDataUpdate(cardPayModel)) {
						throw new DataBaseException();
					}

					// 원 거래 TABLE UPDATE
					if (!paymentMapper.cardPayProcessCancelUpdate(cardPayModel)) {
						throw new DataBaseException();
					}
				} else {
					// 취소 STATUS SETTING
					cardPayModel.setStatus("CANCEL_FAIL");

					// 카드 취소 TABLE UPDATE
					if (!paymentMapper.cardCancelProcessDataUpdate(cardPayModel)) {
						throw new DataBaseException();
					}

					throw new CardCompayException();
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new CardCompayException();
			}
			logger.info("카드사 취소 통신 END");
			/* 카드사 통신 END */

			// 부분취소
		} else if (cancleAbleAmount > cancelRequestAmount) {

			// 부가가치세 null 일 경우 부가가치세 자동 셋팅
			if (payCancelRequestVo.getTax() == null) {
				cancelRequestTax = (int) Math.round((double) payCancelRequestVo.getAmount() / (double) 11);
			}

			// 요청 부가가치세가 취소가능한 부가가치세 보다 클때 에러처리
			if (cancelRequestTax > cancelAbleTax) {
				throw new CancelAmountException(ErrorCode.ER005);
			}

			cardPayModel = paymentMapper.payInquiry(cardPayModel);
			cardPayModel.setStatus("READY");
			cardPayModel.setCancelUniqueId(GenerateIdTool.generateCancelUniqueId());
			cardPayModel.setTransactionId(payCancelRequestVo.getTransactionId());
			// 취소 요청 금액 셋팅
			cardPayModel.setAmount(cancelRequestAmount);
			// 취소 요청 부가세 셋팅
			cardPayModel.setTax(cancelRequestTax);

			try {
				paymentMapper.cardCancelProcessDataInsert(cardPayModel);
			} catch (Exception e) {
				e.printStackTrace();
				throw new DataBaseException();
			}

			logger.info("카드사 취소 전문 생성 START");
			String requestData = CardCompayRequestData.generateCancelData(cardPayModel);
			/* 카드사 전문 생성 END */
			logger.info("카드사 취소 전문 생성 END");

			/* 카드사 통신 START */
			logger.info("카드사 취소 통신 START");
			try {
				logger.info("REQUEST DATA : " + requestData);

				// 카드사 저장
				if (cardCompanyMapper.insertRequestData(requestData)) {
					// 취소 STATUS SETTING
					cardPayModel.setStatus("PRTCCANCEL");
					// 취소 TIMESTAMP 셋팅
					cardPayModel.setCancelDate(new Date());

					// 카드 취소 TABLE UPDATE
					if (!paymentMapper.cardCancelProcessDataUpdate(cardPayModel)) {
						throw new DataBaseException();
					}

					// 원 거래 TABLE UPDATE
					if (!paymentMapper.cardPayProcessCancelUpdate(cardPayModel)) {
						throw new DataBaseException();
					}
				} else {
					// 취소 STATUS SETTING
					cardPayModel.setStatus("CANCEL_FAIL");

					// 카드 취소 TABLE UPDATE
					if (!paymentMapper.cardCancelProcessDataUpdate(cardPayModel)) {
						throw new DataBaseException();
					}

					throw new CardCompayException();
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new CardCompayException();
			}
			logger.info("카드사 취소 통신 END");
			/* 카드사 통신 END */

			// 취소가능 금액 오버
		} else {
			throw new CancelAmountException(ErrorCode.ER004);
		}

		return cardPayModel;
	}

}
