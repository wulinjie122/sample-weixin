package site.eris.test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.DebugMessageHandler;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;

/**
 * Created by wulinjie on 2015/12/3.
 */
public class MessageServerStartup {
	public static void main(String[] args) {
		try{
			new WeixinServerBootstrap("token").addHandler(DebugMessageHandler.global).startup();
		}catch (WeixinException e){
			e.printStackTrace();
		}

	}
}
