package mysite.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mysite.vo.UserVo;

/**
 * UserDetails: Spring Security가 요구하는 반환형에 맞추기 위해 
 * UserDetailsServiceImpl에서 조회한 UserVo를 UserDetails로 체화하는 기능 제공
 * UserDetailsImpl은 인증 과정에서만 사용된다.
 */
public class UserDetailsImpl extends UserVo implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

}
