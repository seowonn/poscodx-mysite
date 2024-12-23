package mysite.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

    public ContextLoadListener() {
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	// 컨텍스트에서 Listener를 가져온다.
    	ServletContext servletContext = sce.getServletContext();
    	String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation"); // /WEB-INF/applicationContext.xml
    	
    	System.out.println("Application[MySite02] starts... " + contextConfigLocation);
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    }
}
