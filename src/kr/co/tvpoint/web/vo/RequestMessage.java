package kr.co.tvpoint.web.vo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class RequestMessage {
	private int currentPage;
	private int countPerPage;
	private String keyword;
	private String resultType;

	public String getAddrApiKey() {
		String propertyPath = "../config/config.properties";

		Properties MessageProp = new Properties();
		String key = null;

		String path;

		try {
			path = Thread.currentThread().getContextClassLoader().getResource("/").toURI().resolve(propertyPath)
					.getPath();
			MessageProp.load(new FileInputStream(path));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		key = MessageProp.getProperty("address_api_key");
		return key;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@Override
	public String toString() {
		return "\n== RequestMessage start ==\n\n" + "currentPage=>" + currentPage + "\n\n countPerPage=>" + countPerPage
				+ "\n\n keyword=>" + keyword.toString() + "\n\n resultType=>" + resultType.toString()
				+ "\n\n== RequestMessage end ==\n";
	}

}