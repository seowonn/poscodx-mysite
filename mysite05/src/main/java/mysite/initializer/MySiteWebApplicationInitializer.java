package mysite.initializer;

import java.util.ResourceBundle;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration.Dynamic;
import mysite.config.AppConfig;
import mysite.config.WebConfig;

public class MySiteWebApplicationInitializer
		extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Root Application Context을 생성하여 반환하는 곳 이어서 ContextLoad Listener를 실행
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	// web.xml의 <servlet-mapping> 태그 역할
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * FilterChainProxy는 SpringSecurity에서 제공되는 특수 필터로 
	 * SpringSecurityFilterChain이라는 이름을 가진 Bean을 호출하여 
	 * SecurityFilter의 역할을 수행한다.
	 */
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new DelegatingFilterProxy("springSecurityFilterChain") };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		ResourceBundle bundle = ResourceBundle.getBundle("mysite.config.web.fileupload");
		long maxFileSize = Long.parseLong(bundle.getString("fileupload.maxFileSize"));
		long maxRequestSize = Long.parseLong(bundle.getString("fileupload.maxRequestSize"));
		int fileSizeThreshold = Integer.parseInt(bundle.getString("fileupload.fileSizeThreshold"));
		MultipartConfigElement config = new MultipartConfigElement(null, maxFileSize,
				maxRequestSize, fileSizeThreshold);
		registration.setMultipartConfig(config);
	}

}
