package notes.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//import notes.dto.UserDtoServer;
import notes.models.UserServer;
import notes.repositories.UsersRepository;

//import notes.dto.UserDtoClient;
//import notes.models.UserClient;
//import lombok.extern.slf4j.Slf4j;
//import notes.rest.client.RestClientUsers;

//@Slf4j
@Configuration
public class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    UserDetailsService userDetailsService(UsersRepository repository) {
		/*
		 * Лямбда-функция реализует метод loadUserByUsername() интерфейса 
		 * UserDetailsService и возвращает службу хранения учетных записей 
		 * пользователей (то есть объект UserDetailsService)
		 * */
		return username -> {
			UserServer user = repository.findByUsername(username);
			if (user != null) {
				return user;
			}
			throw new UsernameNotFoundException("User '"  + username + "' not found!");
		};
	}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http
				.authorizeHttpRequests( (authorizeHttpRequests) ->
						authorizeHttpRequests
							.requestMatchers("/api/notes", "/api/notes/**").authenticated()
							.requestMatchers(HttpMethod.POST, "/api/users").permitAll()
							.requestMatchers("/", "/**").permitAll() )
				
				/*
				 * Ниже следующая настройка исключает (отключает) защиту CSRF при выполнении 
				 * запросов POST, PUT, PATCH, DELETE по ниже указанному пути. При отсутствии 
				 * такой настройки при выполнении запросов POST, PUT, PATCH, DELETE выходит 
				 * ошибка запрета доступа к запрошеному ресурсу по ниже указанному пути: 
				 *   org.springframework.web.client.HttpClientErrorException$Forbidden: 403 : 
				 *   "{"timestamp":"2023-08-30T12:51:25.411+00:00","status":403,"error":
				 *   "Forbidden","message":"Forbidden","path":"/api/notes"}"
				 * */
				.csrf( (csrf) -> csrf.ignoringRequestMatchers("/api/users") )
				
				/* 
				 * При отсутствии ниже следующей настройки ".httpBasic()" выходит ошибка: 
				 * 	 (когда приложение было в виде единого монолита)
				 *   "org.springframework.web.client.UnknownContentTypeException: 
				 *   Could not extract response: no suitable HttpMessageConverter 
				 *   found for response type [class [Lnotes.models.Note;] and content 
				 *   type [text/html;charset=UTF-8]"
				 *   		ИЛИ
				 *   (когда приложение клиента разработано отдельно отдельно от сервера)
				 *   org.springframework.web.client.HttpClientErrorException$Forbidden: 403 : 
				 *   "{"timestamp":"2023-10-15T13:37:15.324+00:00","status":403,"error":
				 *   "Forbidden","message":"Access Denied","path":"/api/notes"}"
				 * */
				.httpBasic(Customizer.withDefaults())
				
				.build();
	}
}
