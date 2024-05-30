package social_bookstore.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import social_bookstore.model.Book;
import social_bookstore.model.UserProfile;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
@Entity
@Table(name="categories")
public class BookCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="category_id")
	private int categoryId;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "bookCategory")
	private List<Book> books;
	
	@ManyToMany(mappedBy = "favoriteBookCategories")
	private List<UserProfile> user;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<UserProfile> getUser() {
		return user;
	}

	public void setUser(List<UserProfile> user) {
		this.user = user;
	}
}
