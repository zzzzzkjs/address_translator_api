package kr.co.tvpoint.web.vo;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestBase {
	private String requestUrl;

	private LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public void addParam(String paramKey, String paramValue) {
		paramMap.put(paramKey, paramValue);
	}

	public void getParam(String paramKey) {
		paramMap.get(paramKey);
	}

	public void clearParam() {
		paramMap.clear();
	}

	public String paramToString(boolean isPost) {
		StringBuffer paramBuffer = new StringBuffer();
		try {

			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				if (paramBuffer.length() == 0)
					paramBuffer.append((isPost ? "" : "?") + key + "=" + value);
				else
					paramBuffer.append("&" + key + "=" + value);
			}

			return paramBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
