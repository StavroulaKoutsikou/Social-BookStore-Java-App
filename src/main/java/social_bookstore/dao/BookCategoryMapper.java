package social_bookstore.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import social_bookstore.model.BookCategory;
public interface BookCategoryMapper extends JpaRepository<BookCategory, Integer>{
	public Optional<BookCategory> findByName(String name);
}
