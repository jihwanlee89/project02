package com.springboot.payment.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class ResponseWrapper extends ContentCachingResponseWrapper {

	byte[] rowData;
	String responseBody;

	public ResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		// TODO Auto-generated constructor stub

		rowData = IOUtils.toByteArray(((ContentCachingResponseWrapper) response).getContentInputStream());
		responseBody = new String(rowData);

	}

}
