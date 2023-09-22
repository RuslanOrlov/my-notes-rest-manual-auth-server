package notes.models;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import notes.dto.UserDtoServer;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonIgnoreProperties(value = {"authorities", "enabled", "credentialsNonExpired", 
								"accountNonExpired", "accountNonLocked"})
/*
 * Аннотация @JsonIgnoreProperties требуется, чтобы обеспечить 
 * десериализацию объектов User, получаемых с сервера. В данном 
 * случае, когда сервер пытается вернуть User, который расширяет 
 * UserDetails, происходит попытка вернуть большой перечень 
 * виртуальных полей, предусмотренных интерфейсом UserDetails. 
 * Аннотация @JsonIgnoreProperties исключает из процесса 
 * десериализации эти поля (см. выше перечень этих полей). 
 * */
public class UserServer implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private final String username;
	private String password;
	private String openpass;
	private String email;
	
	public UserServer(String username, String password, String openpass, String email) {
		this.username = username;
		this.password = password;
		this.openpass = openpass;
		this.email = email;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public UserDtoServer UserToDto() {
		UserDtoServer dto = new UserDtoServer();
		
		dto.setId(this.id);
		dto.setUsername(this.username);
		dto.setPassword(this.password);
		dto.setOpenpass(this.openpass);
		dto.setEmail(this.email);
		
		return dto;
	}
}
