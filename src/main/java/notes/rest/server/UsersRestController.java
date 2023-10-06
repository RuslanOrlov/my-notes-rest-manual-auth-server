package notes.rest.server;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import notes.dto.UserDtoServer;
import notes.models.UserServer;
import notes.repositories.UsersRepository;

@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
//@CrossOrigin(origins = {"http://localhost:8081/", "http://localhost:8082/"})
public class UsersRestController {
	
	private UsersRepository usersRepository;
	private PasswordEncoder encoder;

	public UsersRestController(UsersRepository usersRepository, 
								PasswordEncoder encoder) {
		this.usersRepository = usersRepository;
		this.encoder = encoder;
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserDtoServer postUser(@RequestBody UserDtoServer dto) {
		UserServer user = new UserServer(dto.getUsername(), encoder.encode(dto.getPassword()), 
							dto.getOpenpass(), dto.getEmail());
		user = this.usersRepository.save(user);
		
		dto.setId(user.getId());
		
		return dto;
	}
	
	@GetMapping(params = "username")
	public UserDtoServer getUserByUsername(@RequestParam String username) {
		UserServer user = this.usersRepository.findByUsername(username);
		
		if (user == null)
			return null;
		
		return user.userToDto();
	}
	
}
