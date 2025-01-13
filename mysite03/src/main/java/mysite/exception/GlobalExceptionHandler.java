package mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log log = LogFactory
			.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public void handler(HttpServletRequest request,
			HttpServletResponse response, Model model, Exception e) throws Exception {

		// 1. 로깅(logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		log.error(errors.toString());

		// 2. 요청 구분
		// json 요청: request header의 accept: application/json (o)
		// html 요청: request header의 accept: application/json (x)
		String accept = request.getHeader("accept");

		if (accept.matches("")) {
			// 3. JSON 응답

		} else {
			// 4. 사과 페이지(종료)
			request.setAttribute("errors", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/errors/exception")
					.forward(request, response);
		}

	}
}