package com.springboot;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class Project02ApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {

		MockMvcClientHttpRequestFactory requestFactory = new MockMvcClientHttpRequestFactory(mockMvc);
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(requestFactory);
		
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * 
		 * headers.setContentType(new MediaType("application", "json",
		 * Charset.forName("UTR-8")));
		 */
		/*
		 * ResponseEntity<String> result = restTemplate.getForEntity("/payment/v1/pay",
		 * String.class);
		 */

		/*
		 * restTemplate.getMessageConverters().add(0, new
		 * StringHttpMessageConverter(Charset.forName("UTF-8")));
		 * 
		 * ResponseEntity<String> result1 =
		 * restTemplate.postForEntity("/payment/v1/pay", "{}", String.class);
		 */
		
		String requestJson ="{\"key\" : \"이지환 \"}";
		
		
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
		headers.setContentType(mediaType);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);
		
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		/*
		 * ResponseEntity<String> responseEntity =
		 * restTemplate.exchange("/payment/v1/pay", HttpMethod.POST,
		 * request,String.class);
		 */

		asyncRestTemplate.getForEntity("/payment/v1/pay", String.class);
		//ResponseEntity<String> responseEntity = restTemplate.getForEntity("/payment/v1/pay", String.class);
		
	}

}
