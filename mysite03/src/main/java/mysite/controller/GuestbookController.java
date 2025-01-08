package mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.GuestbookService;
import mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	private GuestbookService guestbookService;
	
	public GuestbookController(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String show(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("id") Long id) {
		return "guestbook/delete";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(
			@RequestParam("id") Long id, @RequestParam("password") String password) {
		guestbookService.deleteContents(id, password);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(GuestbookVo guestbookVo) {
		guestbookService.addContents(guestbookVo);

//		redirect는 클라이언트가 새로운 요청을 하게 만듦으로써 이전 데이터를 재사용하지 않는다.
		return "redirect:/guestbook";
	}
}
