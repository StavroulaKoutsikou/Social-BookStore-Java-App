package social_bookstore.formsdata;
import java.util.List;

import social_bookstore.model.BookAuthor;
import social_bookstore.model.BookCategory;
public class UserProfileFormData {
	private String username;
	private String fullName;
	private String address;
	private int age;
	private long phoneNumber;
	private String favoriteBookAuthors;
	private List<String> favoriteBookCategories;
	private List<String> userNotifications;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<String> getFavoriteBookCategories() {
		return favoriteBookCategories;
	}
	public void setFavoriteBookCategories(List<String> favoriteCategories) {
		this.favoriteBookCategories = favoriteCategories;
	}
	public String getFavoriteBookAuthors() {
		return favoriteBookAuthors;
	}
	public void setFavoriteBookAuthors(String favoriteAuthors) {
		this.favoriteBookAuthors = favoriteAuthors;
	}
	public List<String> getUserNotifications() {
		return userNotifications;
	}
	public void setUserNotifications(List<String> userNotifications) {
		this.userNotifications = userNotifications;
	}

}
