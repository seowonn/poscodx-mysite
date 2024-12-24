package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(email == null || email.length() == 0 || password == null || password.length() == 0) {
			loginFailed(request, response, email);
			return;
		}
		
		UserVo vo = new UserDao().findByEmailAndPassword(email, password);
		if(vo == null) {
			loginFailed(request, response, email);
			return;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", vo);
		response.sendRedirect(request.getContextPath());
	}
	
	private void loginFailed(HttpServletRequest request, HttpServletResponse response, String email) throws ServletException, IOException {
		request.setAttribute("result", "fail");
		if(email != null && email.length() > 0) {
			request.setAttribute("email", email);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
		rd.forward(request, response);
	}

}
