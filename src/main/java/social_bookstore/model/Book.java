package social_bookstore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private int bookId;

    @Column(name="title")
    private String title;

    @Column(name="summary")
    private String summary;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(
            name = "book_written_by_authors",
            joinColumns = @JoinColumn(
                    name = "book_id", referencedColumnName = "book_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "author_id", referencedColumnName = "author_id"
            )
    )
    private List<BookAuthor> bookAuthors;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="book_belongs_to_category",
            joinColumns = @JoinColumn(
                    name = "book_id", referencedColumnName = "book_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "category_id"
            )
    )
    private BookCategory  bookCategory;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_requesting_books",
            joinColumns = @JoinColumn(
                    name = "book_id", referencedColumnName = "book_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "username", referencedColumnName = "username"
            )
    )
    private List<UserProfile> requestingUsers;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<BookAuthor> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<BookAuthor> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

	public List<UserProfile> getRequestingUsers() {
		return requestingUsers;
	}

	public void setRequestingUsers(List<UserProfile> requestingUsers) {
		this.requestingUsers = requestingUsers;
	}


}
