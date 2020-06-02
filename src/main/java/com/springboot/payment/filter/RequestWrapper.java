package com.springboot.payment.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RequestWrapper extends HttpServletRequestWrapper {

	private byte[] rawData;
	private String requestBody;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);

		InputStream in = super.getInputStream();
		this.rawData = IOUtils.toByteArray(in);
		this.requestBody = new String(rawData);
	}

	public RequestWrapper(HttpServletRequest request, String transactionId) throws IOException {
		super(request);

		InputStream in = super.getInputStream();
		this.rawData = IOUtils.toByteArray(in);
		this.requestBody = new String(rawData);

		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		try {
			jsonObject = (JSONObject) jsonParser.parse(this.requestBody);
			jsonObject.put("transaction_id", transactionId);
			this.rawData = jsonObject.toString().getBytes();
			this.requestBody = new String(rawData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getRequestBody() {
		return this.requestBody;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub

			}
		};
		return servletInputStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	@Override
	public ServletRequest getRequest() {
		return super.getRequest();
	}

}
