package site.eris.test.controller;

import com.foxinmy.weixin4j.util.MapUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulinjie on 2015/11/26.
 */
public class IndexControllerTest {

	@Test
	public void testCheckSignature(){
		List<String> params = new ArrayList<String>();
		params.add("token");
		params.add("20151103");
		params.add("ewewe");
		String str = MapUtil.toJoinString(params, false, false, null);
		System.out.println(str);

		String ok;

	}

}
