package mysite.web;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession httpSession = httpRequest.getSession(false);
		String action = httpRequest.getParameter("a");
		
		String[] protectedURLStrings = {"writeform", "write", "updateform", "update"};
		
		if (httpSession == null || httpSession.getAttribute("authUser") == null) {
		    for (String protectedAction : protectedURLStrings) {
		        if (protectedAction.equals(action)) {
		            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user?a=login");
		            return;
		        }
		    }
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
