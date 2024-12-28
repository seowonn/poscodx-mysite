package mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mysite.service.BoardService;
import mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public String show(Model model) {
//		List<BoardVo> list = boardService.getContentsList(0, null);
//		model.addAttribute("list", list);
//		return "board/list";
//	}
	
	
}
