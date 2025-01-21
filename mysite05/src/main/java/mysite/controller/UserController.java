package mysite.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import mysite.security.AuthUser;
import mysite.service.UserService;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/join")
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}

	@PostMapping("/join")
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			Map<String, Object> map = result.getModel();
			model.addAllAttributes(map);
			return "user/join";
		}
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Authentication authentication, Model model) {

//		 1. HttpSession을 사용하는 방법 (권장 X, 원리 설명을 위해 적용)
//	   SecurityContext sc = (SecurityContext) session.getAttribute(
//			HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
//		Authentication authentication = sc.getAuthentication();
//		UserVo authUser = (UserVo) authentication.getPrincipal();
		

//		 2. SecurityContextHolder
//		SecurityContext sc = SecurityContextHolder.getContext();
//		Authentication authentication = sc.getAuthentication();
//		UserVo userVo = (UserVo) authentication.getPrincipal();
//		
		UserVo authUser = (UserVo) authentication.getPrincipal();
		UserVo userVo = userService.getUser(authUser.getId());

		model.addAttribute("vo", userVo);
		return "user/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo userVo) {
		userVo.setId(authUser.getId());
		userService.update(userVo);

		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}

	@RequestMapping("/auth")
	public void auth() {

	}

	@RequestMapping("/logout")
	public void logout() {

	}

}