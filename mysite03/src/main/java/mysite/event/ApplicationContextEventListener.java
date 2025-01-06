package mysite.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import mysite.service.SiteService;

/**
 * 이벤트 핸들러: 스프링 컨텍스트가 초기화되거나 리프레시될 때 발생하는 이벤트
 * @EventListener를 통해 특정 이벤트가 발생했을 때 해당 메서드가 호출됩
 */
public class ApplicationContextEventListener {
	
	@Autowired
	private ApplicationContext applicationContext;

	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		SiteService siteService = applicationContext.getBean(SiteService.class);
		System.out.println("-- Context Refreshed Event Received --" + siteService);		
	}
}
