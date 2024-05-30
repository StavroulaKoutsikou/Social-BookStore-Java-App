package social_bookstore.dao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import social_bookstore.model.Notifications;
public interface NotificationsMapper extends JpaRepository<Notifications, Integer> {
	public Optional<Notifications> findById(int id);
}
