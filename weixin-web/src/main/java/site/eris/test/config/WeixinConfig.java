package site.eris.test.config;

import com.foxinmy.weixin4j.api.BaseApi;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.mp.api.OauthApi;
import com.foxinmy.weixin4j.mp.token.WeixinJSTicketCreator;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.TokenStorager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wulinjie on 2015/11/30.
 */
@Configuration
public class WeixinConfig {

	private TokenStorager fileTokenStorager = new FileTokenStorager("/weixin4j/");

	@Autowired
	private WeixinProxy proxy;

	@Bean
	public WeixinProxy weixinProxy() throws WeixinException {
		WeixinProxy proxy =  new WeixinProxy (fileTokenStorager);
		return proxy;
	}

	@Bean
	public OauthApi oauthApi(){
		return new OauthApi();
	}

	@Bean
	TokenHolder weixinJSTicketTokenHolder() throws WeixinException{
		TokenHolder jsTokenHolder = new TokenHolder(new WeixinJSTicketCreator(BaseApi.DEFAULT_WEIXIN_ACCOUNT.getId(), proxy.getTokenHolder()), fileTokenStorager);
		return jsTokenHolder;
	}
}
