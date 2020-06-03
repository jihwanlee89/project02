package com.springboot.payment.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.payment.mapper.PaymentMapper;
import com.springboot.payment.requestVo.payExecuteVo.CardPayRequestVo;

@Component
@Aspect
public class PaymentAop {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PaymentMapper paymentMapper;

	@After("execution (* com.springboot.payment.service.impl.PaymentServiceImpl.cardPayProcess (..))")
	public void deleteInvalidCard(JoinPoint joinPoint) {

		Object paramObject = joinPoint.getArgs()[0];

		if (paramObject instanceof CardPayRequestVo) {
			CardPayRequestVo cardPayRequestVo = (CardPayRequestVo) paramObject;

		}

		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~.AOP 실행 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	}

}
