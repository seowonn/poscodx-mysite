package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String show(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
		int currentPage = Math.max(1,  page);
		Map<String, Object> map = boardService.getContentsList(currentPage, "");
		map.put("currentPage", currentPage);
	    model.addAttribute("data", map);
		return "board/list";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String getWriteForm(@RequestParam("id") Long id) {
		return id == null ? "board/write" : "board/write?id=" + id;
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@RequestParam("id") Long id, HttpSession session, BoardVo boardVo) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardVo.setUserId(authUser.getId());
		
		if(id != null) {
			
		}
		
		boardService.addContents(boardVo);
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@RequestParam("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id);
		model.addAttribute("vo", boardVo);
		return "board/view";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateForm(HttpSession session, @RequestParam("id") Long id, Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		BoardVo boardVo = boardService.getContents(id, authUser.getId());
		model.addAttribute("id", id);
		model.addAttribute("vo", boardVo);
		return "board/modify";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST) 
	public String update(@RequestParam("id") Long id, BoardVo boardVo) {
		boardVo.setId(id);
		boardService.updateContents(boardVo);
		return "redirect:/board/view?id=" + id; // 브라우저가 해당 요청을 get로 보내게 된다.
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpSession session, @RequestParam("id") Long id) {
	    UserVo authUser = (UserVo) session.getAttribute("authUser");
	    boardService.deleteContents(id, authUser.getId());
	    return "redirect:/board";
	}
	
}
