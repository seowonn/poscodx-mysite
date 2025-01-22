package mysite.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private SiteService siteService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		/**
		 * 인터셉터로 메서드 분리, request scope에 저장의 단점(매 요청 시마다 getSite 쿼리 발생) 보완을 위해 1. request
		 * 보다 더 넓은 범위인 ServletContext에 siteVo 저장 2. null인 경우에만 조회 쿼리 발생
		 */
		SiteVo siteVo = (SiteVo) request.getServletContext().getAttribute("siteVo");
		if (siteVo == null) {
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}
		
		// locale
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		return true;
	}

}
