package mysite.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. Handler 종류 확인: 컨트롤러의 메서드의 핸들러가 아닌 경우(정적 자원 핸들러)에 대한 처리
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletRequestHandler 타입인 경우
			// DefaultServletHandler: 정적자원, /assests/**, mapping이 안되어 있는 URL
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. Handler에서 @Auth 가져오기 (내가 등록해놓은 어노테이션)
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 4. Handler Method에서 @Auth가 없으면 클래스(타입)에서 가져오기 
		if(auth == null) {
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}
		
		// 5. 그럼에도 @Auth가 없을 경우 
		if(auth == null) { // 인증이 필요 없는 경우
			return true;
		}
		
		// 5. @Auth가 붙어 있기 때문에 인증(Authentication) 여부 확인 가능
		// 세션에 있는 role이랑 판단해줘야 함(?)
		String role = auth.role();
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// role이 USER인 사용자는 USER role만 접근가능하다.
		if(!"USER".equals(role) && "USER".equals(authUser.getRole())) {
			return false;
		}
		
		// 5. @Auth가 붙어 있고 인증도 된 경우 + ADMIN인 경우 == 더 이상 처리가 필요없는 상태
		return true;
	}

}
