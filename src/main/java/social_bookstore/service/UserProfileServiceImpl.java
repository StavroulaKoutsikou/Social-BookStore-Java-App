package social_bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import social_bookstore.dao.BookAuthorMapper;
import social_bookstore.dao.BookCategoryMapper;
import social_bookstore.dao.BookMapper;
import social_bookstore.dao.NotificationsMapper;
import social_bookstore.dao.UserProfileMapper;
import social_bookstore.formsdata.BookFormData;
import social_bookstore.formsdata.NotificationsFormData;
import social_bookstore.formsdata.UserProfileFormData;
import social_bookstore.model.Book;
import social_bookstore.model.BookAuthor;
import social_bookstore.model.BookCategory;
import social_bookstore.model.Notifications;
import social_bookstore.model.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    UserProfileMapper userProfileMapper;

    @Autowired
    BookAuthorMapper bookAuthorMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @Autowired
    BookMapper bookMapper;
    
    @Autowired
    NotificationsMapper notificationsMapper;

    
    @Override
    public UserProfileFormData retrieveProfile(String username) {
        Optional<UserProfile> userProfile = userProfileMapper.findByUsername(username);

        if (userProfile.isPresent()) {
            UserProfileFormData userProfileFormData = new UserProfileFormData();
            
            /*"convert" UserProfile objects to UserProfileFormData objects"*/
            userProfileFormData.setUsername(userProfile.get().getUsername());
            userProfileFormData.setFullName(userProfile.get().getFullName());
            userProfileFormData.setAddress(userProfile.get().getAddress());
            userProfileFormData.setAge(userProfile.get().getAge());
            userProfileFormData.setPhoneNumber(userProfile.get().getPhoneNumber());

            String authorNames = userProfile.get().getFavoriteBookAuthors().stream()
                    .map(BookAuthor::getName)
                    .collect(Collectors.joining(", "));

            userProfileFormData.setFavoriteBookAuthors(authorNames);

            List<String> categoryNames = userProfile.get().getFavoriteBookCategories().stream()
                    .map(BookCategory::getName) // Extract the book categories names
                    .collect(Collectors.toList()); // Collect all the names to a list

            userProfileFormData.setFavoriteBookCategories(categoryNames);
            return userProfileFormData;
        } else {
            UserProfileFormData userProfileFormData = new UserProfileFormData();
            userProfileFormData.setUsername(username);
            return userProfileFormData;
        }


    }
    

    @Override
    public void save(UserProfileFormData userProfileFormData) {
        UserProfile userProfile = new UserProfile();

        userProfile.setUsername(userProfileFormData.getUsername());
        userProfile.setFullName(userProfileFormData.getFullName());
        userProfile.setAddress(userProfileFormData.getAddress());
        userProfile.setAge(userProfileFormData.getAge());
        userProfile.setPhoneNumber(userProfileFormData.getPhoneNumber());

        ArrayList<BookAuthor> favoriteBookAuthors = new ArrayList<>();
        String[] names = userProfileFormData.getFavoriteBookAuthors().split(",");

        for (int i = 0; i < names.length; i++) {
            Optional<BookAuthor> author = bookAuthorMapper.findByName(names[i]);

            if (author.isPresent()) {
                favoriteBookAuthors.add(author.get());
            } else {
            	/*If the book author's name doesn't exist, save it*/
                BookAuthor author2 = new BookAuthor();
                author2.setName(names[i]);
                favoriteBookAuthors.add(author2);
            }
        }
        userProfile.setFavoriteBookAuthors(favoriteBookAuthors);

        ArrayList<BookCategory> favoriteBookCategories = new ArrayList<>();

        for (int i = 0; i < userProfileFormData.getFavoriteBookCategories().size(); i++) {
            String categoryName = userProfileFormData.getFavoriteBookCategories().get(i);

            Optional<BookCategory> category = bookCategoryMapper.findByName(categoryName);

            if (category.isPresent()) {
                favoriteBookCategories.add(category.get());
            } else {
            	/*If the book category doesn't exist, save it*/
                BookCategory category2 = new BookCategory();
                category2.setName(categoryName);
                favoriteBookCategories.add(category2);
            }
        }
        userProfile.setFavoriteBookCategories(favoriteBookCategories);


        userProfileMapper.save(userProfile);
    }

    
    @Override
    public List<BookFormData> retrieveBookOffers(String username) {
        Optional<UserProfile> userProfile = userProfileMapper.findByUsername(username);

        List<BookFormData> bookOffers = userProfile.get().getBookOffers().stream()
                .map(book -> {
                    BookFormData bookFormData = new BookFormData();
                    bookFormData.setTitle(book.getTitle());
                    String authorNames = book.getBookAuthors().stream()
                            .map(BookAuthor::getName) //Extract all the book authors' names
                            .collect(Collectors.joining(", ")); //Collect all the book authors to a list by ","
                    
                    bookFormData.setBookId(book.getBookId());
                    bookFormData.setAuthors(authorNames);
                    bookFormData.setCategory(book.getBookCategory().getName());
                    bookFormData.setSummary(book.getSummary());
                    return bookFormData;
                }).collect(Collectors.toList()); //Collect all the book offers to a list

        return bookOffers;
    }


    @Override
    public void addBookOffer(String username, BookFormData bookFormData) {
        
        Optional<UserProfile> userProfile = userProfileMapper.findByUsername(username);
        if (userProfile.isPresent()) {
            UserProfile user = userProfile.get();

            Book newBook = new Book();
            newBook.setTitle(bookFormData.getTitle());

            List<BookAuthor> bookAuthors = new ArrayList<>();
            String[] authorNames = bookFormData.getAuthors().split(",");
            for (String name : authorNames) {
                Optional<BookAuthor> author = bookAuthorMapper.findByName(name.trim());
                BookAuthor bookAuthor = author.orElseGet(() -> { //If the author's name doesn't exist, save it
                    BookAuthor newAuthor = new BookAuthor();
                    newAuthor.setName(name.trim());
                    return newAuthor;
                });
                bookAuthors.add(bookAuthor);
            }
            newBook.setBookAuthors(bookAuthors);

            
            String bookCategory = bookFormData.getCategory();
            Optional<BookCategory> category = bookCategoryMapper.findByName(bookCategory);
            BookCategory bookCategory1 = category.orElseGet(() -> { //If the category name doesn't exist, save it
                BookCategory newCategory = new BookCategory();
                newCategory.setName(bookCategory);
                bookCategoryMapper.save(newCategory);
                return newCategory;
            });
            newBook.setBookCategory(bookCategory1);

            newBook.setSummary(bookFormData.getSummary());

            user.getBookOffers().add(newBook);

            bookMapper.save(newBook);
            userProfileMapper.save(user);
        }
    }


    /*This method is responsible for retrieving all the offered books from other users*/
    @Override
	public List<BookFormData> retrieveAllBook(String username) {
		Optional<UserProfile> userProfile = userProfileMapper.findByUsername(username);

        if (userProfile.isPresent()) {
        	UserProfile user = userProfile.get();
        	
        	List<Book> books = bookMapper.findAll();
    		List<Book> othersBooks = new ArrayList<>();
    		for(Book book : books) {
    			if(user.getBookOffers().contains(book) == false) {
    				othersBooks.add(book);
    			}
    		}
    		
    		/*return a list of the offered books*/
    		return othersBooks.stream()
                    .map(book -> {
                        BookFormData bookFormData = new BookFormData();
                        bookFormData.setTitle(book.getTitle());
                        String authorNames = book.getBookAuthors().stream()
                                .map(BookAuthor::getName)
                                .collect(Collectors.joining(", "));
                        
                        bookFormData.setBookId(book.getBookId());
                        bookFormData.setAuthors(authorNames);
                        bookFormData.setCategory(book.getBookCategory().getName());
                        bookFormData.setSummary(book.getSummary());
                        return bookFormData;
                    }).collect(Collectors.toList());
        	
        }else {
        	return new ArrayList<BookFormData>();
        }
	}
    

	@Override
	public void requestBook(int bookId, String username) {
		Optional<UserProfile> userProfile = userProfileMapper.findByUsername(username);

        if (userProfile.isPresent()) {
        	UserProfile user = userProfile.get();
        	
        	Optional<Book> book = bookMapper.findById(bookId);
        	
        	if (book.isPresent()) {
        		Book b = book.get();
        		if(b.getRequestingUsers().contains(user) == false) {
        			b.getRequestingUsers().add(user);
            		bookMapper.save(b);
        		}
        	}
        }	
	}
	

	@Override
	public List<UserProfileFormData> retrieveRequestingUsers(int bookId) {
		Optional<Book> book = bookMapper.findById(bookId);
    	
    	if (book.isPresent()) {
    		List<UserProfile> requestingUsers = book.get().getRequestingUsers();
    		List<UserProfileFormData> users = new ArrayList<>();
    		
    		for(UserProfile user: requestingUsers) {
    			UserProfileFormData userFormData = new UserProfileFormData();
    			userFormData.setUsername(user.getUsername());
    			userFormData.setFullName(user.getFullName());
    			users.add(userFormData);
    		}
    		return users;
    		
    	} else {
    		return new ArrayList<UserProfileFormData>();
    	}
	}
	
	
	@Override
	public UserProfileFormData selectUser(String selectedUsername, String username) {
		Optional<UserProfile> userProfile = userProfileMapper.findByUsername(username);
		
		if(userProfile.isPresent()) {
			UserProfile currentUser = userProfile.get();
			UserProfileFormData userProfileFormData = new UserProfileFormData();
			Optional<UserProfile> selectedUser = userProfileMapper.findByUsername(selectedUsername);
			
	        if (selectedUser.isPresent()) {
	        	UserProfile user = selectedUser.get(); //Get the user that the logged in user selects to donate the book to
	        	
	        	/*Message for the selected user*/
	            String message = "The book you requested is now available for you to take!";
	            Notifications notification = new Notifications("Book Available", message);
	            
	            /*Get the contact info*/
	            userProfileFormData.setUsername(user.getUsername());
	            userProfileFormData.setAddress(user.getAddress());
	            userProfileFormData.setPhoneNumber(user.getPhoneNumber());
	            
	            notificationsMapper.save(notification);
	            user.getUserNotifications().add(notification);
	            userProfileMapper.save(user);
	            
	            List<Book> offeredBooks = currentUser.getBookOffers();

	            for (Book book : offeredBooks) {
	            	List<UserProfile> requestingUsers = new ArrayList<>(book.getRequestingUsers());
	            	for (UserProfile requestingUser : requestingUsers) {
	            		if (!requestingUser.getUsername().equals(selectedUsername)) {
	            			/*Message for the rest requesting users*/
	            			String message1 = "The book you requested is no longer available.";
                            Notifications notification1 = new Notifications("Book Unavailable", message1);
                            requestingUser.getUserNotifications().add(notification1);
                            userProfileMapper.save(requestingUser);  
	            		}
	            	}
	            } 
	        } 
	        return userProfileFormData;
   
		}else {
			
			UserProfileFormData userProfileFormData = new UserProfileFormData();
            userProfileFormData.setUsername(username);
            return userProfileFormData;
		}
	}
	
	
	@Override
	public List<NotificationsFormData> retrieveNotifications(String username){
		Optional<UserProfile> userProfile = userProfileMapper.findByUsername(username);

        if (userProfile.isPresent()) {
        	List<NotificationsFormData> notifictaions = userProfile.get().getUserNotifications().stream()
                    .map(notification -> {
                    	NotificationsFormData notifFormData = new NotificationsFormData();
                    	notifFormData.setId(notification.getId());
                    	notifFormData.setSubject(notification.getSubject());
                    	notifFormData.setMessage(notification.getMessage());
                    	notifFormData.setTimeStamp(notification.getTimestamp());
                        return notifFormData;
                    }).collect(Collectors.toList());  //Collect all user's notifications to a list
        return notifictaions;

        }else {
        	return new ArrayList<NotificationsFormData>();  
        }   
	}
	
	
	@Override
	public void deleteBookOffer(String username, int bookId) {
		
		Optional<UserProfile> userProfile = userProfileMapper.findByUsername(username);

        if (userProfile.isPresent()) {
        	UserProfile user = userProfile.get();
        	
        	Optional<Book> book = bookMapper.findById(bookId);
        	
        	if (book.isPresent()) {
        		Book b = book.get();
        		user.getBookOffers().remove(b);
        		userProfileMapper.save(user);
        		
        		/*Clear the list of requesting users for the book*/
	            b.getRequestingUsers().clear();
	            
	            /*Delete the book from the database*/
	            bookMapper.delete(b);
        	}
        }
	}	
}