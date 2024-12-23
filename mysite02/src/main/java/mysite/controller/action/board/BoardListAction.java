package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardListAction implements Action {
	
	static final int DEFAULT_PAGE_SIZE = 10;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		int offset = (currentPage - 1) * DEFAULT_PAGE_SIZE;
		
		List<BoardVo> list = new BoardDao().findAll(DEFAULT_PAGE_SIZE, offset);
		request.setAttribute("list", list);
		
		int totalPages = (int) Math.ceil((double) new BoardDao().count() / DEFAULT_PAGE_SIZE);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPages", totalPages);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
