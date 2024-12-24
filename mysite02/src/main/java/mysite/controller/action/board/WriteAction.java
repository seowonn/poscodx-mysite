package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.ReplyBoardVo;
import mysite.vo.UserVo;

public class WriteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		
		String gNo = request.getParameter("g_no");
		String oNo = request.getParameter("o_no");
		String depth = request.getParameter("depth");
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if(authUser != null) {
			Long userId = authUser.getId();
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserName(depth);
			vo.setUserId(userId);
			
			if(gNo == null && oNo == null && depth == null) {
				new BoardDao().insert(vo);
			} else {
				// 답글 달기 write인 경우
				ReplyBoardVo replyBoardVo = new ReplyBoardVo();
				
				replyBoardVo.setgNo(Integer.parseInt(gNo));
				replyBoardVo.setoNo(Integer.parseInt(oNo));
				replyBoardVo.setDepth(Integer.parseInt(depth));
				replyBoardVo.setTitle(title);
				replyBoardVo.setContents(contents);
				replyBoardVo.setUserId(userId);
				new BoardDao().insertBoardReply(replyBoardVo);
			}
		}
		
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
