package social_bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import social_bookstore.formsdata.BookFormData;
import social_bookstore.formsdata.NotificationsFormData;
import social_bookstore.formsdata.UserProfileFormData;
import social_bookstore.model.Book;
import social_bookstore.model.BookAuthor;
import social_bookstore.model.BookCategory;
import social_bookstore.model.UserProfile;
import social_bookstore.service.UserProfileService;
import social_bookstore.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class UserController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserProfileService userProfileService;

    @RequestMapping("/user/dashboard")
    public String getUserMainMenu(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String currentPrincipalName = authentication.getName();
        
        System.err.println(currentPrincipalName);
        
         return "user/dashboard";
    }
    
    @RequestMapping("/user/MyProfile")
    public ModelAndView getUserMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserProfileFormData user = userProfileService.retrieveProfile(currentPrincipalName);
        return new ModelAndView("user/MyProfile","profiles",user);
    }
    
    @GetMapping("/user/profile")
    public String retrieveProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        UserProfileFormData userProfile = userProfileService.retrieveProfile(currentPrincipalName);
        model.addAttribute("userprofile", userProfile);
        return "user/profile";
    }
    
    @PostMapping("/user/profile")
    public String saveProfile(UserProfileFormData userProfileFormData, Model theModel) {
        System.out.println(userProfileFormData.getUsername());
        userProfileService.save(userProfileFormData);
        UserProfileFormData userProfile = userProfileService.retrieveProfile(userProfileFormData.getUsername());
        theModel.addAttribute("profiles", userProfile);
        return "redirect:/user/MyProfile";
    }
    
    @PostMapping("/user/bookOffer")
    public String saveOffer(@ModelAttribute("bookform") BookFormData bookFormData, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        userProfileService.addBookOffer(currentPrincipalName, bookFormData);
        return "redirect:/user/MyBookOffer";
    }

    @RequestMapping("/user/bookOffer")
    public String showOfferForm(Model model) {
        BookFormData bookForm = new BookFormData();
        model.addAttribute("bookform", bookForm); 
        return "user/bookOffer";
    }
    
    @GetMapping("/user/MyBookOffer")
    public String listBookOffers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        List<BookFormData> bookFormDataList = userProfileService.retrieveBookOffers(currentPrincipalName);

        
        model.addAttribute("books", bookFormDataList);

        return "user/MyBookOffer";
    }
    
    @RequestMapping("/user/all")
    public String listAllBookOffers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        List<BookFormData> bookFormDataList = userProfileService.retrieveAllBook(currentPrincipalName);

        model.addAttribute("books", bookFormDataList);
        
         return "user/all";
    }

    @RequestMapping("/user/requestBook")
    public String requestBook(@RequestParam("bookId") int theBookId) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        userProfileService.requestBook(theBookId, currentPrincipalName);
        
        return "redirect:/user/all";
    }

    @RequestMapping("/user/showRequestingUsersForBookOffer")
    public String showRequestingUsersForBookOffer(@RequestParam("bookId") int theBookId, Model model) {
        List<UserProfileFormData> userDataList = userProfileService.retrieveRequestingUsers(theBookId);
        model.addAttribute("users", userDataList);
        return "user/requestingUsers";
    }
    
    @RequestMapping("/user/selectUser")
    public String acceptRequest( @RequestParam("username")String selectedUsername, Model model){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
    	UserProfileFormData userProfile = userProfileService.selectUser(selectedUsername, currentPrincipalName);
    	
    	model.addAttribute("user" ,userProfile);
    	return "user/selectedUser";
    }
    

    @RequestMapping("/user/notifications")
    public String showNotifications(String username, Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = authentication.getName();
        
        List<NotificationsFormData> userNotifications = userProfileService.retrieveNotifications(username);
        model.addAttribute("notifications", userNotifications);
    	return "user/notifications";
    }
    
    
    @RequestMapping("/user/deleteBookOffer")
    public String deleteBookOffer(String username, @RequestParam("bookId") int bookId, Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = authentication.getName();
        List<BookFormData> bookFormDataList = userProfileService.retrieveBookOffers(username);
        List<UserProfileFormData> usersList = userProfileService.retrieveRequestingUsers(bookId);
        List<BookFormData> allBooks = userProfileService.retrieveAllBook(username);
        
        userProfileService.deleteBookOffer(username, bookId);
        
        model.addAttribute("books", bookFormDataList);
    	return "redirect:/user/MyBookOffer";
    }
}
