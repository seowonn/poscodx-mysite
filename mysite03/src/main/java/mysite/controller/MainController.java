package mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import mysite.vo.SiteVo;

@Controller
public class MainController {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@RequestMapping({"/", "/main"})
	public String main(Model model) {		
		SiteVo vo = applicationContext.getBean(SiteVo.class);
		return "main/index";
	}
}