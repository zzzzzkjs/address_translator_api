package kr.co.tvpoint.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tvpoint.web.service.HttpRequest;
import kr.co.tvpoint.web.vo.RequestBase;
import kr.co.tvpoint.web.vo.RequestMessage;

@RestController
public class AddressController {
	public static final String ADDR_ENG_URL = "https://www.juso.go.kr/addrlink/addrEngApi.do";

	@RequestMapping("/")
	public String welcome() {
		return "Welcome! address_translator_V1 server is running.";
	}

	// 메세지
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addrEng", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody HashMap<String, Object> addrEng(@RequestBody RequestMessage requestMessage) {

		System.out.println("[/address/eng]" + requestMessage.toString());

		try {

			RequestBase request = new RequestBase();
			request.setRequestUrl("https://www.juso.go.kr/addrlink/addrEngApi.do");

			request.addParam("confmKey", requestMessage.getAddrApiKey()); // Key 프로퍼티 파일로 숨기기
			request.addParam("keyword", URLEncoder.encode(requestMessage.getKeyword(), "UTF-8"));
			request.addParam("currentPage", Integer.toString(requestMessage.getCurrentPage()));
			request.addParam("countPerPage", Integer.toString(requestMessage.getCountPerPage()));
			request.addParam("resultType", requestMessage.getResultType());
			HttpRequest httpReq = new HttpRequest();
			JSONObject addrResponse = httpReq.requestPost(request);

			System.out.println(addrResponse.toJSONString());

			return addrResponse;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			HashMap<String, Object> responseJson = new HashMap<String, Object>();

			responseJson.put("resultCode", "-9999");
			responseJson.put("resultMessage", "파라미터 형식 오류");
			return responseJson;
		}
	}

}
