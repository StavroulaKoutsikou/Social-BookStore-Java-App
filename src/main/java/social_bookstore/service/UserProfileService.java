package social_bookstore.service;

import java.util.List;

import social_bookstore.formsdata.BookFormData;
import social_bookstore.formsdata.NotificationsFormData;
import social_bookstore.formsdata.UserProfileFormData;

public interface UserProfileService {
    UserProfileFormData retrieveProfile(String username);

    void save(UserProfileFormData userProfileFormData);

    List<BookFormData> retrieveBookOffers(String username);

    void addBookOffer(String username, BookFormData bookFormData);
	
	List<BookFormData> retrieveAllBook(String username);

	void requestBook(int bookId, String username);

	List<UserProfileFormData> retrieveRequestingUsers(int bookId);
	
	UserProfileFormData selectUser(String selectedUsername, String username);
	
	List<NotificationsFormData> retrieveNotifications(String username);
	
	void deleteBookOffer(String username, int bookId);
	
}

	

	

	




	
	




	