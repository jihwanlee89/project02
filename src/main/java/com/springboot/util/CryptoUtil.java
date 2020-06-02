package com.springboot.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import com.springboot.payment.constant.PaymentConstant;

public class CryptoUtil {

	public static String seedCbcEncrypt(String plainText) {

		String encryte = null;

		try {
			byte[] enc = KISA_SEED_CBC.SEED_CBC_Encrypt(PaymentConstant.payment_pbszUserKey.getBytes(),
					PaymentConstant.payment_pbszIV.getBytes(), plainText.getBytes("utf-8"), 0,
					plainText.getBytes("utf-8").length);

			Encoder encoder = Base64.getEncoder();
			byte[] encArray = encoder.encode(enc);

			encryte = new String(encArray, "utf-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return encryte;
	}

	public static String seedCbcDecrypt(String encryptText) {

		String decrypt = null;

		try {
			Decoder decoder = Base64.getDecoder();
			byte[] enc = decoder.decode(encryptText);

			byte[] dec = KISA_SEED_CBC.SEED_CBC_Decrypt(PaymentConstant.payment_pbszUserKey.getBytes(),
					PaymentConstant.payment_pbszIV.getBytes(), enc, 0, enc.length);

			decrypt = new String(dec, "utf-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return decrypt;
	}
}
