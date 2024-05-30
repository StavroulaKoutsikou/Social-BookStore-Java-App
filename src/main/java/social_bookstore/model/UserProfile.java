package social_bookstore.model;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import social_bookstore.model.Book;
import social_bookstore.model.BookAuthor;
import social_bookstore.model.BookCategory;
import social_bookstore.model.Notifications;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;


@Entity
@Table(name="profiles")
public class UserProfile {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="username", nullable=false)
	private String username;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="age")
	private int age;
	
	@Column(name="phone_number")
	private long phoneNumber;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name = "user_favorite_authors",
		joinColumns = @JoinColumn(name = "username" , referencedColumnName = "username"),
		inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName =  "author_id") 
	)
	private List<BookAuthor> favoriteBookAuthors;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name = "user_favorite_categories",
		joinColumns = @JoinColumn(name = "username", referencedColumnName =  "username"),
		inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName =  "category_id")
	)
	private List<BookCategory> favoriteBookCategories;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name = "book_offers",
			joinColumns = @JoinColumn(name = "username", referencedColumnName =  "username"),
			inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName =  "book_id")
	)
	private List<Book> bookOffers;
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name = "user_notifications",
			joinColumns = @JoinColumn(name = "username", referencedColumnName =  "username"),
			inverseJoinColumns = @JoinColumn(name = "notif_id", referencedColumnName =  "id")
	)
	private List<Notifications> userNotifications;	
	
	public UserProfile() {
        this.bookOffers = new ArrayList<>();
        this.userNotifications = new ArrayList<>();
    }

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<BookAuthor> getFavoriteBookAuthors() {
		return favoriteBookAuthors;
	}

	public void setFavoriteBookAuthors(List<BookAuthor> favoriteBookAuthors) {
		this.favoriteBookAuthors = favoriteBookAuthors;
	}

	public List<BookCategory> getFavoriteBookCategories() {
		return favoriteBookCategories;
	}

	public void setFavoriteBookCategories(List<BookCategory> favoriteBookCategories) {
		this.favoriteBookCategories = favoriteBookCategories;
	}

	public List<Book> getBookOffers() {
		return bookOffers;
	}

	public void setBookOffers(List<Book> bookOffers) {
		this.bookOffers = bookOffers;
	}

	public List<Notifications> getUserNotifications() {
		return userNotifications;
	}

	public void setUserNotifications(List<Notifications> userNotifications) {
		this.userNotifications = userNotifications;
	}
	
	 

}


