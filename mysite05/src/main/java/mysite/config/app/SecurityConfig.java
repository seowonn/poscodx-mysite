package mysite.config.app;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.repository.UserRepository;
import mysite.security.UserDetailsServiceImpl;

@Configuration
/**
 * @EnableWebSecurity 
 * * DelegatingFilterProxy를 통해 Spring Security 필터 체인을 구성하고 HTTP 요청이 이 체인을 거치도록한다. 
 * * Method Security를 활성화하기 위해 필요할 경우 Proxy 객체를 생성하여 인증 및 권한 부여 로직을 적용한다. 
 * * Security Context 기반으로 접근 제어가 가능하다.
 */
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> {
				csrf.disable();
			})
			.formLogin((formLogin) -> {
				formLogin.loginPage("/user/login")
						.loginProcessingUrl("/user/auth")
						.usernameParameter("email")
						.passwordParameter("password")
						.defaultSuccessUrl("/")
//							.failureUrl("/user/login?result=fail");
						.failureHandler(
								new AuthenticationFailureHandler() {
									@Override
									public void onAuthenticationFailure(HttpServletRequest request,
											HttpServletResponse response, AuthenticationException exception)
											throws IOException, ServletException {
										request.setAttribute("email", request.getParameter("email"));
										request.setAttribute("result", "fail");
										request.getRequestDispatcher("/user/login")
										.forward(request, response);
									}
								}
						);
				}).authorizeHttpRequests((authorizeRequests) -> {
				/* ACL */
				authorizeRequests
						.requestMatchers(
								new RegexRequestMatcher("^/user/updates$", null))
						.authenticated()
						.anyRequest()
						.permitAll();
			});
		return http.build();
	}

	/**
	 * AuthenticationManager
	 * 로그인 요청을 처리하는 UsernamePasswordAuthenticationFilter에서 인증을 수행하기 위해 사용된다.
	 * 인증에 성공할 경우, Authentication 객체를 생성하고 Principal(사용자 정보)을 제공하며,
	 * 이를 SecurityContext에 저장하여 이후 요청에서 인증 상태를 유지한다.
	 */
	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService, PasswordEncoder passwordEncode) {
		/**
		 * 일반적인 인증 수행 방식으로 DaoAuthenticationProvider가 동작하며,
		 * UserDetailsService를 통해 DB 내 사용자 조히를 통해 사용자를 검증한다.
		 */
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncode);
		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4); // 4 ~ 31  
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return new UserDetailsServiceImpl(userRepository);
	}

}
