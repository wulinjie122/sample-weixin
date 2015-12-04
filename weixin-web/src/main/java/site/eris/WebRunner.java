package site.eris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by wulinjie on 2015/11/26.
 */
@SpringBootApplication
@EnableAsync
public class WebRunner extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebRunner.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebRunner.class, args);
	}
}
