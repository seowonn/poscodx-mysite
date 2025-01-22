package mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.dto.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public void handler(HttpServletRequest request, HttpServletResponse response,
			Model model, Exception e) throws Exception {

		// 1. 로깅(logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		log.error(errors.toString());

		// 2. 요청 구분
		// json 요청: request header의 accept: application/json (o)
		// html 요청: request header의 accept: application/json (x)
		String accept = request.getHeader("accept");

		if (accept.matches(".*application/json.*")) {
			
			if(e instanceof NoHandlerFoundException) {				
				System.out.println("accept: " + accept);
			}

			// 3. JSON 응답
			JsonResult jsonResult = JsonResult
					.fail(e instanceof NoHandlerFoundException ? "Unknown API URL"
							: errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);

			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			// OutputStream: 응답 Body에 데이터를 추가하기 위한 객체
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();

			return;
		}

		// 4. HTML 응답: 사과 페이지(종료)
		if (e instanceof NoHandlerFoundException || e instanceof NoResourceFoundException) {
			request.getRequestDispatcher("/error/404").forward(request,response);
		} else {
			request.setAttribute("errors", errors.toString());
			request.getRequestDispatcher("/error/500")
					.forward(request, response);
		}
	}
}