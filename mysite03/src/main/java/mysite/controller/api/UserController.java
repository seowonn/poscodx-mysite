package mysite.controller.api;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mysite.dto.JsonResult;
import mysite.service.UserService;
import mysite.vo.UserVo;

@RestController("userApiController") // RestController는 뷰를 반환하던 Controller에 문자를 보낼 수 있는
				// ResponseBody가 합쳐진 것이다.
@RequestMapping("/api/user")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/checkemail")
	public JsonResult checkEmail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		UserVo userVo = userService.getUser(email);
		System.out.println(userVo != null);
		return JsonResult.success(Map.of("exist", userVo != null));
	}
}
