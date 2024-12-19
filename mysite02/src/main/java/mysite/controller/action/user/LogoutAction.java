package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import mysite.controller.ActionServlet.Action;

public class LogoutAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		// JSP를 무조건 거쳐서 오는 요청이기에 사실 상 HttpSession이 null로 올 수가 없다.
		// 그럼에도 우선 작성한 경우.
		if(session != null) {
			// 로그아웃 처리
			session.removeAttribute("authUser");
			session.invalidate();
		}
		
		response.sendRedirect(request.getContextPath());
	}

}
