package com.springboot.util;

public class CommonUtil {

	public static String cardNumberMasking(String cardNumber) {

		String cardNumberFirst = cardNumber.substring(0, 6);
		String cardMasking = cardNumber.substring(6, cardNumber.length() - 3).replaceAll(".", "*");
		String cardNumberLast = cardNumber.substring(cardNumber.length() - 3, cardNumber.length());

		return cardNumberFirst + cardMasking + cardNumberLast;
	}
}
