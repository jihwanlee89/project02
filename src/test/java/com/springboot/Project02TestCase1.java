package com.springboot;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class Project02TestCase1 {

	@Autowired MockMvc mockMvc;
	
	
	@Test
	void testCase1() throws ParseException {

		MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

		JSONParser jsonParser = new JSONParser();

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
		headers.setContentType(mediaType);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// -------------------------------------------------------payPramSetting----------------------------------------//

		String uniqueId = "";

		JSONObject payParam = new JSONObject();
		payParam.put("amount", 11000);
		payParam.put("tax", 1000);
		payParam.put("cardNumber", "12345678901234");
		payParam.put("cardExpire", "2111");
		payParam.put("cardCvc", "888");
		payParam.put("cardQuota", 11);

		// -------------------------------------------------------payPramSetting----------------------------------------//
		HttpEntity<String> request = new HttpEntity<String>(payParam.toJSONString(), headers);
		ResponseEntity<String> result1 = restTemplate.postForEntity("/payment/v1/pay", request, String.class);

		JSONObject responseJson = (JSONObject) jsonParser.parse(result1.getBody());
		uniqueId = (String) responseJson.get("uniqueId");

		// -------------------------------------------------------cancelPramSetting----------------------------------------//
		JSONObject partCancel1 = new JSONObject();
		partCancel1.put("amount", 1100);
		partCancel1.put("tax", 100);
		partCancel1.put("uniqueId", uniqueId);

		JSONObject partCancel2 = new JSONObject();
		partCancel2.put("amount", 3300);
		partCancel2.put("uniqueId", uniqueId);

		JSONObject partCancel3 = new JSONObject();
		partCancel3.put("amount", 7000);
		partCancel3.put("uniqueId", uniqueId);

		JSONObject partCancel4 = new JSONObject();
		partCancel4.put("amount", 6600);
		partCancel4.put("tax", 700);
		partCancel4.put("uniqueId", uniqueId);

		JSONObject partCancel5 = new JSONObject();
		partCancel5.put("amount", 6600);
		partCancel5.put("tax", 600);
		partCancel5.put("uniqueId", uniqueId);

		JSONObject partCancel6 = new JSONObject();
		partCancel6.put("amount", 100);
		partCancel6.put("uniqueId", uniqueId);
		// -------------------------------------------------------cancelPramSetting----------------------------------------//

		try {
			request = new HttpEntity<String>(partCancel1.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);
		} catch (Exception e) {

		}

		try {
			request = new HttpEntity<String>(partCancel2.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);
		} catch (Exception e) {

		}
		try {
			request = new HttpEntity<String>(partCancel3.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);
		} catch (Exception e) {

		}

		try {
			request = new HttpEntity<String>(partCancel4.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);
		} catch (Exception e) {

		}

		try {
			request = new HttpEntity<String>(partCancel5.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);
		} catch (Exception e) {

		}
		try {
			request = new HttpEntity<String>(partCancel6.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);

		} catch (Exception e) {

		}
		
		// -------------------------------------------------------거래조회PramSetting----------------------------------------//
		try {
			request = new HttpEntity<String>("", headers);
			restTemplate.getForEntity("/payment/v1/pay?uniqueId="+uniqueId, String.class);

		} catch (Exception e) {

		}
		// -------------------------------------------------------거래조회PramSetting----------------------------------------//
	}
	
}
