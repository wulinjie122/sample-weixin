package site.eris.test.spring;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.*;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring 3.x新增了PropertySource解析properties文件
 * Created by wulinjie on 2015/12/4.
 */
public class PropertySourceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	/**
	 * 数据源的用法
	 */
	public void test01(){
		//新建一个属性值
		Map<String, Object> map = new HashMap<>();
		map.put("encoding", "gbk");
		//新建一个属性源
		PropertySource mapPropSour = new MapPropertySource("map", map);
		ResourcePropertySource resourcePropSour;

		logger.info("mapPropSour : " + mapPropSour.getProperty("encoding").toString());

		try{
			resourcePropSour = new ResourcePropertySource("resource", "classpath:resources.properties"); //name, location
			logger.info("resourcePropSour : " + resourcePropSour.getProperty("encoding").toString());

			//组合各种属性源（查找顺序即注册顺序）
			CompositePropertySource compositePropertySource = new CompositePropertySource("composite");
			compositePropertySource.addPropertySource(resourcePropSour);
			compositePropertySource.addPropertySource(mapPropSour);

			logger.info("composite : " + compositePropertySource.getProperty("encoding"));

			//多属性源
			MutablePropertySources mutablePropertySources = new MutablePropertySources();
			mutablePropertySources.addFirst(mapPropSour);
			mutablePropertySources.addLast(resourcePropSour);
			//根据数据源名称获取该数据源下的响应的属性
			logger.info("mutablePropertySources : " + mutablePropertySources.get("resource").getProperty("encoding"));


		}catch (IOException e){
			logger.error("解析属性源出错", e);
		}

	}

	@Test
	/**
	 * 数据源解析器
	 */
	public void test02(){
		//新建一个属性值
		Map<String, Object> map = new HashMap<>();
		map.put("encoding", "gbk");
		//新建一个属性源
		PropertySource mapPropSour = new MapPropertySource("map", map);
		//新建一个多数据源
		MutablePropertySources propertySources = new MutablePropertySources();
		propertySources.addFirst(mapPropSour);
		//初始化数据源解析器
		PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySources);
		//开始解析数据源
		logger.info(propertyResolver.getProperty("encoding"));
		logger.info(propertyResolver.resolvePlaceholders("hi look,the default encoding is ${encoding}"));
		logger.info(propertyResolver.getProperty("no-no","default"));
	}

}
