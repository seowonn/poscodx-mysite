package mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 기본적으로  해당 클래스와 위치한 패키지와 그 하위 패키지를 스캔하여 Spring Bean으로 등록함
public class MySiteApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MySiteApplication.class, args);
	}

}