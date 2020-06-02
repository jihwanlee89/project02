package com.springboot.payment.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.payment.component.GenerateIdTool;

public class PaymentFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		RequestWrapper requestWrapper;
		String transaction_id = GenerateIdTool.generateTransactionID();

		MDC.clear();
		MDC.put("TRANSACTION_ID", transaction_id);

		logger.info("================ Rquest ================");
		logger.info("RequestURL : [" + ((HttpServletRequest) request).getRequestURL() + "]");
		logger.info("ContentType : [" + request.getContentType() + "], CharacterEncoding : ["
				+ ((HttpServletRequest) request).getCharacterEncoding() + "], Method ["
				+ ((HttpServletRequest) request).getMethod() + "]");

		if (((HttpServletRequest) request).getMethod().equals("GET")) {
			requestWrapper = new RequestWrapper((HttpServletRequest) request);
			logger.info("Request Logging START");
			logger.info(requestWrapper.getQueryString());
			logger.info("Request Logging END");

		} else {
			requestWrapper = new RequestWrapper((HttpServletRequest) request, transaction_id);
			logger.info("Request Logging START");
			logger.info(requestWrapper.getRequestBody());
			logger.info("Request Logging END");
		}

		chain.doFilter(requestWrapper, response);

		return;
	}

}
