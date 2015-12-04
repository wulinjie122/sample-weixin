package site.eris.controller;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.OauthApi;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.mp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import site.eris.test.service.WXUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信相关
 * Created by wulinjie on 2015/11/30.
 */
@Controller
public class WXUserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OauthApi oauthApi;
	@Autowired
	private WXUserService weixService;

	/**
	 * 网页授权回调
	 * @param request
	 * @return
	 * @throws WeixinException
	 */
	@RequestMapping("/weixin/authorize-callback")
	public String authorizeCallback(HttpServletRequest request) throws WeixinException{
		String code = request.getParameter("code");					//code
		String state = request.getParameter("state");				//state
		OauthToken oauthToken = weixService.getOauthToken(code);	//通过code换取网页授权access_token
		String openId = oauthToken.getOpenId();						//用户唯一标识
		String token = oauthToken.getAccessToken();					//网页授权接口调用凭证

		request.getSession().setAttribute("openId", openId);

		logger.info("current open_id : " + openId);

		User user = weixService.retrieveUserInfo(token, openId);	//拉取用户信息

		logger.info("userNickName : " + user.getNickName());

		return "redirect:" + state;
	}

}
