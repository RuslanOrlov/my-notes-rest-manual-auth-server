package notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import notes.models.UserServer;

public interface UsersRepository extends JpaRepository<UserServer, Long>{
	
	UserServer findByUsername(String username);
	
}
