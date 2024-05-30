package social_bookstore.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import social_bookstore.model.UserProfile;

public interface UserProfileMapper extends JpaRepository<UserProfile, Integer>{
	public Optional<UserProfile> findByUsername(String username);
}
