package com.springboot;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
class Project02TestCase3 {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void testCase3() throws ParseException {

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
		payParam.put("amount", 20000);
		payParam.put("cardNumber", "12345678901234");
		payParam.put("cardExpire", "2111");
		payParam.put("cardCvc", "888");
		payParam.put("cardQuota", 0);

		// -------------------------------------------------------payPramSetting----------------------------------------//
		HttpEntity<String> request = new HttpEntity<String>(payParam.toJSONString(), headers);
		ResponseEntity<String> result1 = restTemplate.postForEntity("/payment/v1/pay", request, String.class);

		JSONObject responseJson = (JSONObject) jsonParser.parse(result1.getBody());
		uniqueId = (String) responseJson.get("uniqueId");

		// -------------------------------------------------------cancelPramSetting----------------------------------------//
		JSONObject partCancel1 = new JSONObject();
		partCancel1.put("amount", 10000);
		partCancel1.put("tax", 1000);
		partCancel1.put("uniqueId", uniqueId);

		JSONObject partCancel2 = new JSONObject();
		partCancel2.put("amount", 10000);
		partCancel2.put("tax", 909);
		partCancel2.put("uniqueId", uniqueId);

		JSONObject partCancel3 = new JSONObject();
		partCancel3.put("amount", 10000);
		partCancel3.put("uniqueId", uniqueId);
		// -------------------------------------------------------cancelPramSetting----------------------------------------//

		logger.info("취소 1 시작----------------------------------------------------------------");
		try {
			request = new HttpEntity<String>(partCancel1.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);
		} catch (Exception e) {

		}
		logger.info("취소 1 종료----------------------------------------------------------------");
		
		logger.info("취소 2 시작----------------------------------------------------------------");
		try {
			request = new HttpEntity<String>(partCancel2.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);
		} catch (Exception e) {

		}
		
		logger.info("취소 2 종료----------------------------------------------------------------");
		
		
		logger.info("취소3 시작----------------------------------------------------------------");
		try {
			request = new HttpEntity<String>(partCancel3.toJSONString(), headers);
			result1 = restTemplate.postForEntity("/payment/v1/pay/cancel", request, String.class);
		} catch (Exception e) {

		}
		
		logger.info("취소3 종료----------------------------------------------------------------");
		
		logger.info("거래조회 시작----------------------------------------------------------------");
		try {
			request = new HttpEntity<String>("", headers);
			restTemplate.getForEntity("/payment/v1/pay?uniqueId="+uniqueId, String.class);

		} catch (Exception e) {

		}
		logger.info("거래조회 종료----------------------------------------------------------------");
	}
	
}
