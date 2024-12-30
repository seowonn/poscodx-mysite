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
		// 1. Handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletRequestHandler 타입인 경우
			// DefaultServletHandler가 처리하는 경우 (정적자원, /assests/**, mapping이 안되어 있는 URL)
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. @Auth (내가 등록해놓은 어노테이션)
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// 4. @Auth가 없을 경우 
		if(auth == null) { // 인증이 필요없는 경우
			return true;
		}
		
		// 5. @Auth가 붙어 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 5. @Auth가 붙어 있고 인증도 된 경우
		return true;
	}

}
