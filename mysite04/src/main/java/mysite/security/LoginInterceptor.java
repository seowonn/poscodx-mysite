package mysite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.service.UserService;
import mysite.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserVo authUser = userService.getUser(email, password);
		if (authUser == null) {
			request.setAttribute("email", email);
			request.setAttribute("result", "fail");
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request,
					response);
			return false;
		}

		/* login 처리 */
		HttpSession session = request.getSession();
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());

		/**
		 * return false의 의미 요청 처리가 Interceptor까지만 진행되고 요청을 이후의 Interceptor나 Controller로
		 * 전달하지 않음. preHandle 내부에서의 작업(예: forward나 redirect)이 실행되기 때문. Interceptor 내부에서
		 * 필요한 처리(login)를 모두 끝냈기 때문에 이후 요청 흐름이 필요 없고, 동작에 문제가 없음.
		 */
		System.out.println("[authUser]: " + authUser);
		return false;
	}

}
