package notes.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Можно обойтись и без этого класса конфигурации и без класса AuthFilter, 
 * так как Spring Security сам распознает аутентификационные данные на сервере
 * */

@Configuration
public class ServerAccessConfig {

    @Bean
    FilterRegistrationBean<AuthFilter> authFilterRegistration(AuthFilter filter) {
		FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>(filter);
		registration.addUrlPatterns("/api/notes", "/api/notes/*");
		return registration;
	}
	
}