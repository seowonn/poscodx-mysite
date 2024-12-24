package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.ParentBoardVo;

public class WriteFormAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("id") != null) {
			// 여긴 답글 달기로 눌러서 온 곳으로 부모 게시글의 g_no, o_no, depth를 구해서 넘겨줘야 한다. 
			Long id = Long.parseLong(request.getParameter("id"));

			ParentBoardVo vo = new BoardDao().getParentBoardInfo(id);
			request.setAttribute("parent_board_info", vo);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
		rd.forward(request, response);
	}

}
