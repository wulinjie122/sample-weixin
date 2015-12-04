package site.eris.controller;

import com.foxinmy.weixin4j.api.BaseApi;
import com.foxinmy.weixin4j.mp.api.OauthApi;
import com.foxinmy.weixin4j.util.DigestUtil;

import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import site.eris.test.service.WXUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class IndexController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String TOKEN = "DJHID231LDJKSF9";

	@Autowired
	private OauthApi oauthApi;

	@Resource(name="wxUserService")
	private WXUserService wxUserService;

	@RequestMapping("")
	public String index(){
		return "index";
	}

	/**
	 * 测试微信服务器接入
	 * @author wulinjie
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/weixin", method = RequestMethod.GET)
	@ResponseBody
	public String weixinForGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("开始接入微信服务器......");
		//接入微信服务器
		String sign = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		logger.info("signature:" + sign + ",timestamp:" + timestamp + ",nonce:" + nonce);

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
			return echostr;
		} else {
			//处理接收消息
			return "";
		}
	}

	/**
	 * 测试微信服务器事件接收
	 * @author wulinjie
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/weixin", method = RequestMethod.POST)
	@ResponseBody
	public String weixinForPost(@RequestBody String msg,HttpServletRequest request) throws IOException{
		logger.info("开始接入微信事件......");
		Document reqXml  = null;
		Document respXml = null;
		try {
			reqXml = DocumentHelper.parseText(msg);
			Element root = reqXml.getRootElement();
			String to = root.element("ToUserName").getText();
			respXml = DocumentHelper.createDocument();
			Element toUseName = respXml.addElement("ToUserName");
			toUseName.setText("sdfdfd");
			String result = respXml.asXML();
			System.out.println("...");
		}catch (DocumentException e){
			logger.error("", e);
		}
		return "<xml>\n" +
				"<ToUserName><![CDATA[toUser]]></ToUserName>\n" +
				"<FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
				"<CreateTime>12345678</CreateTime>\n" +
				"<MsgType><![CDATA[text]]></MsgType>\n" +
				"<Content><![CDATA[你好]]></Content>\n" +
				"</xml>";
	}



	/**
	 * 测试用户网页授权
	 * @author wulinjie
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("authorize")
	public String indexSign(HttpServletRequest req, Model model) throws IOException{
		logger.info("开始获取用户网页授权......");

		String appId = BaseApi.DEFAULT_WEIXIN_ACCOUNT.getId();		//微信app_id
		String redirectUri = Weixin4jConfigUtil.getValue("user_oauth_redirect_uri");	//网页授权回调的url
		String url = oauthApi.getAuthorizeURL(appId,"http://wulinjie122.imwork.net" + redirectUri, "/", "snsapi_userinfo");
		model.addAttribute("oauthUrl", url);
		return "oauth";
	}

}
