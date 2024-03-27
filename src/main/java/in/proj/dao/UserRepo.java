package in.proj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.proj.model.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer> {
	
	User findByEmailAndPassword(String email, String password);
}
