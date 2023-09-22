package notes.dto;

import lombok.Data;

@Data
public class UserDtoServer {
	private Long id;
	private String username;
	private String password;
	private String openpass;
	private String email;
}
