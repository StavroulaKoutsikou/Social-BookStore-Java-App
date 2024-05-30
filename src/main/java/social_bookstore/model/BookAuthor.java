package social_bookstore.model;

import java.util.List;

//import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import social_bookstore.model.Book;
import social_bookstore.model.UserProfile;

@Entity
@Table(name="authors")
public class BookAuthor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="author_id")
	private int authorId;
	
	@Column(name="author_name")
	private String name;
	
	@ManyToMany(mappedBy = "bookAuthors")
	List<Book> books;
	
	@ManyToMany(mappedBy = "favoriteBookAuthors")
	private List<UserProfile> user;

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBook() {
		return books;
	}

	public void setBook(List<Book> book) {
		this.books = book;
	}

	public List<UserProfile> getUser() {
		return user;
	}

	public void setUser(List<UserProfile> user) {
		this.user = user;
	}
}
