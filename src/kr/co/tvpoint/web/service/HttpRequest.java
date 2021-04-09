package kr.co.tvpoint.web.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import kr.co.tvpoint.web.vo.RequestBase;

public class HttpRequest {
	public String requestGet(RequestBase request) {
		URL url = null;
		URLConnection conn;
		BufferedReader br;
		String inputLine;
		StringBuffer resultStr;

		try {
			url = new URL(request.getRequestUrl() + request.paramToString(false));
			conn = url.openConnection();

			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setUseCaches(false);

			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			resultStr = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				resultStr.append(inputLine);
			}

			br.close();
			return resultStr.toString();
		} catch (Exception e) {
			// if (logger.isErrorEnabled())
			System.out.println("requestGet Exception|" + e.getMessage() + "|" + request.getRequestUrl()
					+ request.paramToString(false));

			e.printStackTrace();
		} finally {
			url = null;
			conn = null;
			br = null;
			inputLine = null;
			resultStr = null;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public JSONObject requestPost(RequestBase request) {
		JSONObject responseObject = new JSONObject();
		URL url = null;
		URLConnection conn = null;
		InputStream isr = null;
		String buf;
		BufferedReader br;
		OutputStream ops = null;
		StringBuffer resultBuf = new StringBuffer();
//		String serverUrl = getServerUrl();

		try {
			url = new URL(request.getRequestUrl());
			conn = url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			conn.setRequestProperty("Content-Type", "application/json");
			ops = conn.getOutputStream();
			ops.write(request.paramToString(true).getBytes());
			ops.flush();
			ops.close();

			isr = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(isr, "UTF-8"));

			while ((buf = br.readLine()) != null) {
				resultBuf.append(buf);
			}

			br.close();

			responseObject = (JSONObject) JSONValue.parse(new StringReader(resultBuf.toString()));

			if (responseObject == null) {
//			if (responseObject == null || (responseObject != null && responseObject.get("resultCode") == null)) {
				responseObject = new JSONObject();
				responseObject.put("resultCode", -1999);
				responseObject.put("resultMessage", "통신오류발생");
			}

			return responseObject;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("requestPost Exception|" + request.getRequestUrl() + "|" + e.getMessage());
		} finally {
			buf = null;
			br = null;
			responseObject = null;
			url = null;
			conn = null;
			isr = null;
			resultBuf = null;
			ops = null;
		}
		return null;
	}
}
