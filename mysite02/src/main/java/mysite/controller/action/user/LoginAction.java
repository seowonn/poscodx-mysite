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
		
		UserVo vo = new UserDao().findByEmailAndPassword(email, password);
		
		// 로그인 실패
		if(vo == null) {
//			response.sendRedirect(request.getContextPath() + "/user?a=loginform");

			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
			rd.forward(request, response);
		}
		
		// 로그인 처리
		/*
		 * 원리: SessionManager 내, JSESSIONID, HttpSession Map을 통해 로그인 유무를 확인한다.
		 * */
		/*
			 HttpSession에 반환할 내용이 없는 경우, SessionManager에서 JSESSIONID 기반의 
			 HttpSession을 새로 만들어서 반환해준다. 
			 단 위 조건은 request.getSession 내부 변수를 true로 설정해야 가능하다.
			 브라우저 별로 JSESSIONID가 다르다.
		*/
		HttpSession session = request.getSession(true);
		// HttpSession 내 AttributeMap에 정보를 저장하는 과정 
		session.setAttribute("authUser", vo);
		
		response.sendRedirect(request.getContextPath());
	}

}
