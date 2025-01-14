package mysite.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
// 컨테이너가 properties 확장명 파일에 접근하여 특정 필드에 주입하게 해주는 어노테이션
@PropertySource("classpath:mysite/config/web/fileupload.properties")
public class FileUploadConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env; // properties 속성 주입 받는 변수

	@Bean
	// Multipart Resolver
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(env.getProperty("fileupload.resourceUrl") + "/**")
				.addResourceLocations(
						"file:" + env.getProperty("fileupload.uploadLocation") + "/");
	}

}
