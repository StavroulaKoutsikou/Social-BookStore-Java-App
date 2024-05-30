package social_bookstore.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notifications")
public class Notifications {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
	    @Column
	    private String subject;

	    @Column
	    private String message;

	    @Column
	    private LocalDateTime timestamp;
	    
	    
	    public Notifications() {
	        this.timestamp = LocalDateTime.now();
	    }
		
		public Notifications(String subject, String message) {
	        this.subject = subject;
	        this.message = message;
	        this.timestamp = LocalDateTime.now();
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}


		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		
	    
}
