package mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@Controller
@ComponentScan(basePackages = {"mysite.service, mysite.repository, mysite.aspect"})
public class AppConfig {

}
