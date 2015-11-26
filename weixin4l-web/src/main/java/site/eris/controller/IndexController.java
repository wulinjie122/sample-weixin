package site.eris.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String TOKEN = "DJHID231LDJKSF9";

	@RequestMapping("")
	public String index(){
		return "index";
	}

	@RequestMapping("checkSignature")
	public void checkSignature(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("开始接入微信服务器......");
		//接入微信服务器
		String sign = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		logger.info("signature:" + sign + ",timestamp:" + timestamp + ",nonce:" + nonce);

		//JSONObject json = JSON.parseObject("{}");
		//json.put("token", TOKEN);
		//json.put("timestamp", timestamp);
		//json.put("nonce", nonce);
		//String str = MapUtil.toJoinString(json, false, false, null);
		//String result =
		List<String> params = new ArrayList<String>();
		params.add(TOKEN);
		params.add(timestamp);
		params.add(nonce);
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		String temp =DigestUtil.SHA1(params.get(0) + params.get(1) + params.get(2));

		logger.info("sha1加密：" + temp);

		if (temp.equals(sign)) {
			response.getWriter().write(echostr);
		} else {
			//处理接收消息
		}
	}

}
