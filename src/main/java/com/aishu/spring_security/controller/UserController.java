package com.aishu.spring_security.controller;

import com.aishu.spring_security.Repository.NotificationRepository;
import com.aishu.spring_security.Repository.UserRepo;
import com.aishu.spring_security.model.Notification;
import com.aishu.spring_security.model.User;
import com.aishu.spring_security.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class UserController {


    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    JwtStoreService jwtStoreService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/signup")
    public String register(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes,Model model) {
        try {
            if (userRepo.existsByUsername(user.getUsername())) {
                redirectAttributes.addFlashAttribute("exist username", "Username already exists!");
                System.out.println("User: " + user.getUsername() + " Already Present");
                redirectAttributes.addFlashAttribute("present", true);
                redirectAttributes.addFlashAttribute("message", "User "+user.getUsername()+" already present");
                return "redirect:/login";
            }
            if (userRepo.existsByPhone(user.getPhone())) {
                System.out.println("User: " + user.getPhone() + " Already Present");
                redirectAttributes.addFlashAttribute("present", true);
                redirectAttributes.addFlashAttribute("message", "User "+user.getPhone()+" already present");
                return "redirect:/login";
            }
            // try saving user
            userService.saveUser(user);
            System.out.println("User: " + user.getUsername() + " NotPresent So, Added");
            // if save succeeds
            logger.info("new user created {}", user.getFirstName());
            //notificationService.addSignupNotification(user.getFirstName());

            Notification note = new Notification();
            note.setTitle("New User Signup");
            note.setMessage("User " + user.getFirstName() + " signed up successfully");
            note.setTimeAgo(LocalDateTime.now()); // later you can calculate dynamically
            notificationRepository.save(note);

            // Push notification to admin channel
            messagingTemplate.convertAndSend("/topic/adminNotifications", note);

//            model.addAttribute("signupSuccess", "User created successfully!");
//            return "signup"; // not redirect

            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "User " + user.getUsername() + " created successfully! Please login.");
            return "redirect:/login";

        } catch (DataIntegrityViolationException e) {
            // DB constraint violation (duplicate key, etc.)
            model.addAttribute("present",true);
            model.addAttribute("message", "Duplicate data detected. Please check your details.");
            return "redirect:/login";
       }
        catch (Exception e) {
            // any other error
            redirectAttributes.addFlashAttribute("present",true);
            redirectAttributes.addFlashAttribute("message", "Account creation failed!");
            return "redirect:/signup";
        }
    }

    @PostMapping("/perform_login")
    public String login(@ModelAttribute("user") User user, Model model, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            String accessToken = jwtService.generateAccessToken(user.getUsername());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            //db storage
            tokenService.storeRefreshToken(user.getUsername(),refreshToken,LocalDateTime.now().plusDays(7)); //expiry matches claims

            System.out.println("refreshToken: "+refreshToken);
            System.out.println("accessToken: "+accessToken);

            jwtStoreService.storeToken(user.getUsername(), accessToken);

            response.addCookie(CookieUtil.createAccessTokenCookie(accessToken));
            response.addCookie(CookieUtil.createRefreshTokenCookie(refreshToken));

            System.out.println("login success");

            return "redirect:/dashboard";
        } else {
            model.addAttribute("present", true);
            model.addAttribute("message", "Invalid Username or Password!");
            return "redirect:/login";
        }
    }

}
