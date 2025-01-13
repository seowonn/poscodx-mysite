package mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import mysite.config.web.MvcConfig;

@Configuration
@EnableAspectJAutoProxy
@Import({MvcConfig.class})
@ComponentScan({"mysite.controller", "mysite.exception"})
public class WebConfig {
	
}
