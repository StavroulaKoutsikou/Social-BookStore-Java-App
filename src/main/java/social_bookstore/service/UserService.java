package social_bookstore.service;

import org.springframework.stereotype.Service;

import social_bookstore.model.User;

@Service
public interface UserService {
	public void saveUser(User user);
    public boolean isUserPresent(User user);
    public User findById(String username);
}
