package site.eris.controller;

import com.foxinmy.weixin4j.mp.api.OauthApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 微信消息管理(接收、发送)
 * Created by wulinjie on 2015/12/3.
 */
@Controller
@RequestMapping("/wxmsg")
public class WXMsgController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OauthApi oauthApi;

	/**
	 * 接收文本消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/receiveTextMsg")
	@ResponseBody
	public String receiveTextMessage(HttpServletRequest request){
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()){
			logger.info(enumeration.nextElement());
		}
		return "";
	}
}
