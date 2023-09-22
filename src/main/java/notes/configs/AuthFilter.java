package notes.configs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import notes.models.UserServer;
import notes.repositories.UsersRepository;

/*
 * Можно обойтись и без этого класса фильтра и без класса ServerAccessConfig, 
 * так как Spring Security сам распознает аутентификационные данные на сервере
 * */

@Component
@Getter
@Setter
@Slf4j
public class AuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private UsersRepository repository;
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
								throws ServletException, IOException {
		// Извлекаем заголовок "Authorization"
		String authHeader = request.getHeader("Authorization");
		
		log.info("!!! authHeader        - " + authHeader);
		
		if (authHeader != null && authHeader.startsWith("Basic")) {
			// Извлекаем значение заголовка "Authorization"
			String base64Credentials = authHeader.substring("Basic".length()).trim();
			
			log.info("!!! base64Credentials - " + base64Credentials);
			
			// Декодируем credentials
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, Charset.forName("UTF-8"));

			log.info("!!! credentials       - " + credentials);
			
			// Извлекаем логин и пароль
			final String[] values = credentials.split(":",2);
			String username = values[0];
			String password = values[1];
			
			log.info("!!! username - " + values[0] + ", password - " + values[1]);
			
			// Проверяем данные
			if (this.isValid(username, password)) {
				// Аутентификация прошла успешно
				// Разрешаем доступ
				filterChain.doFilter(request, response);
			}
			else {
				// Аутентификация не прошла
				// Возвращаем HTTP 401
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		} else {
			// Заголовок Authorization отсутствует
			// Возвращаем HTTP 401 
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	
	public boolean isValid(String username, String password) {
		// Здесь обращение к БД, проверка логина и пароля
		UserServer user = this.repository.findByUsername(username);
		
		if (user != null) {
			return encoder.matches(password, user.getPassword());
		}
		
		return false;
	}
}
