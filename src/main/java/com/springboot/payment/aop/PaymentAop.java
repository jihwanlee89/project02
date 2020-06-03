package com.springboot.payment.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.payment.exception.code.ErrorCode;
import com.springboot.payment.exception.customException.DataBaseException;
import com.springboot.payment.exception.customException.TransactionLockException;
import com.springboot.payment.mapper.PaymentMapper;
import com.springboot.payment.model.TransactionLockModel;
import com.springboot.payment.requestVo.payCancelVo.PayCancelRequestVo;
import com.springboot.payment.requestVo.payExecuteVo.CardPayRequestVo;
import com.springboot.util.CryptoUtil;

@Component
@Aspect
public class PaymentAop {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PaymentMapper paymentMapper;

	@Before("execution (* com.springboot.payment.service.impl.PaymentServiceImpl.cardPayProcess (..))")
	public void checkCardLock(JoinPoint joinPoint) throws DataBaseException {
		Object paramObject = joinPoint.getArgs()[0];

		if (paramObject instanceof CardPayRequestVo) {
			CardPayRequestVo cardPayRequestVo = (CardPayRequestVo) paramObject;

			String transactionKey = CryptoUtil.seedCbcEncrypt((cardPayRequestVo.getCardNumber()
					+ cardPayRequestVo.getCardExpire() + cardPayRequestVo.getCardCvc()));

			logger.info("CARD LOCK KEY : " + transactionKey);

			TransactionLockModel transactionControllModel = new TransactionLockModel();
			transactionControllModel.setTransactionKey(transactionKey);
			transactionControllModel.setTransactionId(cardPayRequestVo.getTransactionId());

			try {
				logger.info("CARD LOCK SELECT START");
				int checkCount = paymentMapper.lockKeySelect(transactionControllModel);
				logger.info("CARD LOCK SELECT : " + String.valueOf(checkCount));
				logger.info("CARD LOCK SELECT END");
				
				// 해당 CARD번호로 결제중인지 체크
				if (checkCount > 0) {
					throw new TransactionLockException(ErrorCode.ER008);
				}
				
				logger.info("CARD LOCK INSERT START");
				logger.info("CARD LOCK INSERT : " + String.valueOf(paymentMapper.lockKeyInsert(transactionControllModel))); 
				logger.info("CARD LOCK INSERT END");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DataBaseException();
			}
		}
	}

	@After("execution (* com.springboot.payment.service.impl.PaymentServiceImpl.cardPayProcess (..))")
	public void deleteCardLock(JoinPoint joinPoint) throws DataBaseException {

		logger.info("CARD LOCK DELETE START");
		Object paramObject = joinPoint.getArgs()[0];

		if (paramObject instanceof CardPayRequestVo) {
			CardPayRequestVo cardPayRequestVo = (CardPayRequestVo) paramObject;

			String transactionKey = CryptoUtil.seedCbcEncrypt((cardPayRequestVo.getCardNumber()
					+ cardPayRequestVo.getCardExpire() + cardPayRequestVo.getCardCvc()));

			logger.info("CARD LOCK KEY" + transactionKey);

			TransactionLockModel transactionControllModel = new TransactionLockModel();
			transactionControllModel.setTransactionKey(transactionKey);
			transactionControllModel.setTransactionId(cardPayRequestVo.getTransactionId());

			try {
				logger.info("CARD  LOCK : " + String.valueOf(paymentMapper.lockKeyDelete(transactionControllModel)));
			} catch (Exception e) {
				e.printStackTrace();
				throw new DataBaseException();
			}
		}
		logger.info("CARD  LOCK DELETE END");
	}

	@Before("execution (* com.springboot.payment.service.impl.PaymentServiceImpl.cancelProcess (..))")
	public void checkCancelLock(JoinPoint joinPoint) throws DataBaseException {
		Object paramObject = joinPoint.getArgs()[0];

		if (paramObject instanceof PayCancelRequestVo) {
			PayCancelRequestVo payCancelRequestVo = (PayCancelRequestVo) paramObject;

			String transactionKey = payCancelRequestVo.getUniqueId();
			logger.info("CANCEL LOCK : " + transactionKey);

			TransactionLockModel transactionControllModel = new TransactionLockModel();
			transactionControllModel.setTransactionKey(transactionKey);
			transactionControllModel.setTransactionId(payCancelRequestVo.getTransactionId());

			try {
				logger.info("CANCEL LOCK SELECT START");
				int checkCount = paymentMapper.lockKeySelect(transactionControllModel);
				logger.info("CANCEL LOCK SELECT : " + String.valueOf(checkCount));
				logger.info("CANCEL LOCK SELECT END");
				
				// 해당 CARD로 결제중인지 체크
				if (checkCount > 0) {
					throw new TransactionLockException(ErrorCode.ER009);
				}
				
				logger.info("CANCEL LOCK INSERT START");
				logger.info("CANCEL LOCK INSERT : " + String.valueOf(paymentMapper.lockKeyInsert(transactionControllModel))); 
				logger.info("CANCEL LOCK INSERT END");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DataBaseException();
			}
		}
	}

	@After("execution (* com.springboot.payment.service.impl.PaymentServiceImpl.cancelProcess (..))")
	public void deleteCancelLock(JoinPoint joinPoint) throws DataBaseException {

		logger.info("CANCEL LOCK DELETE START");
		Object paramObject = joinPoint.getArgs()[0];

		if (paramObject instanceof PayCancelRequestVo) {
			PayCancelRequestVo payCancelRequestVo = (PayCancelRequestVo) paramObject;

			String transactionKey = payCancelRequestVo.getUniqueId();

			logger.info("CANCEL LOCK KEY : " + transactionKey);

			TransactionLockModel transactionControllModel = new TransactionLockModel();
			transactionControllModel.setTransactionKey(transactionKey);
			transactionControllModel.setTransactionId(payCancelRequestVo.getTransactionId());

			try {
				logger.info("CANCEL LOCK : " + String.valueOf(paymentMapper.lockKeyDelete(transactionControllModel)));
			} catch (Exception e) {
				e.printStackTrace();
				throw new DataBaseException();
			}
		}
		logger.info("CANCEL LOCK DELETE END");
	}
}
