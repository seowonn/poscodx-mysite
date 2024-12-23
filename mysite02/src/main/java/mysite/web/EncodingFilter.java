package mysite.web;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;

public class EncodingFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	private String encoding;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/* request */
		System.out.println("EncodingFilter.doFilter() called: request processing");
		request.setCharacterEncoding("utf-8");
		
		chain.doFilter(request, response);
		
		/* response */
		System.out.println("EncodingFilter.doFilter() called: response processing");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
		if(encoding == null) {
			encoding = "utf-8";
		}
	}

}
