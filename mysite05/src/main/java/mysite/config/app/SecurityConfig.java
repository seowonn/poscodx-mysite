package mysite.config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
/**
 * @EnableWebSecurity * DelegatingFilterProxy를 통해 Spring Security 필터 체인을 구성하고
 *                    HTTP 요청이 이 체인을 거치도록한다. * Method Security를 활성화하기 위해 필요할 경우
 *                    Proxy 객체를 생성하여 인증 및 권한 부여 로직을 적용한다. * 또한 Security Context
 *                    기반으로 접근 제어가 가능하다.
 */
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin((formLogin) -> {
			formLogin.loginPage("/user/login");
		}).authorizeHttpRequests((authorizeRequests) -> {
			/*ACL*/
			authorizeRequests
					.requestMatchers(new RegexRequestMatcher("^/user/updates$", null))
					.authenticated()
					.anyRequest()
					.permitAll();
		});
		return http.build();
	}

}
