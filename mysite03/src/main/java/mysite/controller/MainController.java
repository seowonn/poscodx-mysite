package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import mysite.service.SiteService;

@Controller
public class MainController {
	
	private final SiteService siteService;
	
	public MainController(SiteService siteService) {
		this.siteService = siteService;
	}
	
	@RequestMapping({"/", "/main"})
	public String main(Model model, HttpServletRequest request) {		
		model.addAttribute("siteVo", siteService.getSite());
		return "main/index";
	}
}