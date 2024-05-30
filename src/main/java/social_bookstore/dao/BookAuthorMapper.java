package social_bookstore.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import social_bookstore.model.BookAuthor;

public interface BookAuthorMapper extends JpaRepository<BookAuthor, Integer>{
	public Optional<BookAuthor> findByName(String name);
}
