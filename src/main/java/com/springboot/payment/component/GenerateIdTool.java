package com.springboot.payment.component;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class GenerateIdTool {
	
	public static String generateCancelUniqueId() {
		String uniqueId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
		return uniqueId;
	}
	
	public static String generatePayUniqueId() {
		String uniqueId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
		return uniqueId;
	}

	public static String generateTransactionID() {
		String uniqueId = UUID.randomUUID().toString();
		return uniqueId;
	}
}
