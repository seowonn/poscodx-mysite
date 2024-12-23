package mysite.controller;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public abstract class ActionServlet extends HttpServlet {

	// factoryMethod
	// 외부 클래스(서블릿)에서 상속받아서 구현하게 하도록 protected으로 둠
	protected abstract Action getAction(String actionName);
	
	// operation
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 반복되는 알고리즘을 정의함
//		request.setCharacterEncoding("utf-8");
		String actionName = Optional.ofNullable(request.getParameter("a")).orElse("");
		
		Action action = getAction(actionName);
		action.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public static interface Action {
		void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	}

}
