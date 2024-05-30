package social_bookstore.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import social_bookstore.model.Book;

public interface BookMapper extends JpaRepository<Book, Integer> {
	public Optional<Book> findByTitle(String title);
	public Optional<Book> findByTitleContaining(String title);
	

}