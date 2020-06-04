package com.springboot.payment.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.payment.component.GenerateIdTool;

public class PaymentFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	ObjectMapper objectMapper = new ObjectMapper();

	@SuppressWarnings("deprecation")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		RequestWrapper requestWrapper;
		String transactionId = GenerateIdTool.generateTransactionID();

		MDC.clear();
		MDC.put("TRANSACTION_ID", transactionId);

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
			requestWrapper = new RequestWrapper((HttpServletRequest) request, transactionId);
			logger.info("Request Logging START");
			logger.info(requestWrapper.getRequestBody());
			logger.info("Request Logging END");
		}

		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
				(HttpServletResponse) response);
		
		responseWrapper.setCharacterEncoding("utf-8");
		
		chain.doFilter(requestWrapper, responseWrapper);

		logger.info("================ Response ================");
		logger.info("ContentType : [" + responseWrapper.getContentType() + "]");
		logger.info("Response Logging START");
		logger.info(IOUtils.toString(responseWrapper.getContentInputStream()));
		logger.info("Response Logging END");

		responseWrapper.copyBodyToResponse();

		return;
	}

}
