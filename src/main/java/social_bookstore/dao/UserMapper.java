package social_bookstore.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import social_bookstore.model.User;

public interface UserMapper extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);
}
