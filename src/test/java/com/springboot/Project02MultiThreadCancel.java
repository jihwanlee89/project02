package com.springboot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

public class Project02MultiThreadCancel {

	public static void main(String[] args) {

		for (int index = 0; index < 20; index++) {
			new Thread(new TesterCancel()).start();
		}
	}

}

class TesterCancel implements Runnable {

	@Override
	public void run() {
		try {

			String strUrl = "http://localhost:8080/payment/v1/pay/cancel";

			JSONObject cancelParam = new JSONObject();
			cancelParam.put("amount", 200);
			cancelParam.put("uniqueId", "61659545c50a4e37a5a8");

			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000); // 서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestMethod("POST");

			// json으로 message를 전달하고자 할 때
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoInput(true);
			con.setDoOutput(true); // POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(cancelParam.toJSONString()); // json 형식의 message 전달
			wr.flush();
			
			System.out.println(con.getResponseCode());
			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				System.out.println("" + sb.toString());
			} else {
				System.out.println(con.getResponseMessage());
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
}