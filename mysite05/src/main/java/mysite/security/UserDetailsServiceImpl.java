package mysite.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mysite.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// UserDetails: Spring Security가 요구하는 반환형
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		/**
		 * UserVo(조회된 사용자)를 현재 바로 반환할 수 없음 따라서 UserDetails 인터페이스를 이용하여 해결
		 * 두번째 인자 지정: 특정 엔터티 또는 인터페이스 타입의 결과를 반환받기 위해 지시한 것
		 */
//		UserVo userVo = userRepository.findByEmail(username, UserDetailsImpl.class);
		return userRepository.findByEmail(username, UserDetailsImpl.class);
	}

}
