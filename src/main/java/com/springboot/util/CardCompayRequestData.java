package com.springboot.util;

import org.apache.commons.lang3.StringUtils;

import com.springboot.payment.model.CardPayModel;

public class CardCompayRequestData {

	public static String generateCardPayData(CardPayModel cardPayModel) throws Exception {

		// 카드정보
		StringBuilder encrytData = new StringBuilder();
		encrytData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardNumber()));
		encrytData.append("|");
		encrytData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardExpire()));
		encrytData.append("|");
		encrytData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardCvc()));

		/* 카드정보 암호화 */
		String encrypt = CryptoUtil.seedCbcEncrypt(encrytData.toString());

		/* 전문생성 */
		StringBuilder resultData = new StringBuilder();
		// 데이터 구분
		resultData.append(StringUtils.rightPad("PAYMENT", 10, " "));
		// 관리번호
		resultData.append(cardPayModel.getUniqueId());
		// 카드번호
		resultData.append(StringUtils.rightPad(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardNumber()), 20, " "));
		// 할부개월
		resultData.append(StringUtils.leftPad((String.valueOf(cardPayModel.getCardQuota())), 2, "0"));
		// 카드 유효기간
		resultData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardExpire()));
		// 카드 CVC
		resultData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardCvc()));
		// 결제/취소금액
		resultData.append(StringUtils.leftPad(String.valueOf(cardPayModel.getAmount()), 10, " "));
		// 결제/취소금액의 부가세
		resultData.append(StringUtils.leftPad(String.valueOf(cardPayModel.getTax()), 10, "0"));
		// 원거래 관리번호
		resultData.append(StringUtils.rightPad("", 20, " "));
		// 암호화된카드정보
		resultData.append(StringUtils.rightPad(encrypt, 300, " "));
		// 예비필드
		resultData.append(StringUtils.rightPad("", 47, " "));
		// 데이터길이
		resultData.insert(0, StringUtils.leftPad(String.valueOf(resultData.toString().length()), 4, " "));

		return resultData.toString();
	}

	public static String generateCancelData(CardPayModel cardPayModel) throws Exception {

		// 카드정보
		StringBuilder encrytData = new StringBuilder();
		encrytData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardNumber()));
		encrytData.append("|");
		encrytData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardExpire()));
		encrytData.append("|");
		encrytData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardCvc()));

		/* 카드정보 암호화 */
		String encrypt = CryptoUtil.seedCbcEncrypt(encrytData.toString());

		/* 전문생성 */
		StringBuilder resultData = new StringBuilder();
		// 데이터 구분
		resultData.append(StringUtils.rightPad("CANCEL", 10, " "));
		// 관리번호
		resultData.append(cardPayModel.getCancelUniqueId());
		// 카드번호
		resultData.append(StringUtils.rightPad(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardNumber()), 20, " "));
		// 할부개월
		resultData.append(StringUtils.leftPad("00", 2, "0"));
		// 카드 유효기간
		resultData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardExpire()));
		// 카드 CVC
		resultData.append(CryptoUtil.seedCbcDecrypt(cardPayModel.getEncCardCvc()));
		// 결제/취소금액
		resultData.append(StringUtils.leftPad(String.valueOf(cardPayModel.getAmount()), 10, " "));
		// 결제/취소금액의 부가세
		resultData.append(StringUtils.leftPad(String.valueOf(cardPayModel.getTax()), 10, "0"));
		// 원거래 관리번호
		resultData.append(StringUtils.rightPad(cardPayModel.getUniqueId(), 20, " "));
		// 암호화된카드정보
		resultData.append(StringUtils.rightPad(encrypt, 300, " "));
		// 예비필드
		resultData.append(StringUtils.rightPad("CANCEL_UNIQUE_ID=" + cardPayModel.getCancelUniqueId(), 47, " "));
		// 데이터길이
		resultData.insert(0, StringUtils.leftPad(String.valueOf(resultData.toString().length()), 4, " "));

		return resultData.toString();
	}
}
