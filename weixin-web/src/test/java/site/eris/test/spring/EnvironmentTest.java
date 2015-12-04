package site.eris.test.spring;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

/**
 * Spring Environment Demo
 * Created by wulinjie on 2015/12/4.
 */
public class EnvironmentTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	/**
	 * 输出J2SE 标准环境属性
	 */
	public void test01(){
		//会自动注册 System.getProperties() 和 System.getenv()
		Environment environment = new StandardEnvironment();
		logger.info(environment.getProperty("file.encoding"));
	}

}
