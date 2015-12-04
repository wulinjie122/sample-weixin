package site.eris.test.service;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.OauthApi;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.mp.type.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wulinjie on 2015/11/30.
 */
@Service("wxUserService")
@Transactional
public class WXUserService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OauthApi oauthApi;

	public OauthToken getOauthToken(String code){
		OauthToken oauthToken = new OauthToken();
		try{
			oauthToken = oauthApi.getOauthToken(code);	//通过code换取网页授权access_token
		}catch (WeixinException e){
			logger.error("获取网页授权access_token出错", e);
		}
		return oauthToken;
	}

	/**
	 * 获取微信用户信息
	 * @param token
	 * @param openId
	 * @return
	 */
	public User retrieveUserInfo(String token,String openId){
		User user = new User();
		try{
			user = oauthApi.getUser(token , openId, Lang.zh_CN);
		}catch (WeixinException e){
			logger.error("获取用户错误", e);
		}
		return user;
	}


}
