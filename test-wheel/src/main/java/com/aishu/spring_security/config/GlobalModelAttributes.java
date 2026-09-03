package com.aishu.spring_security.config;

import com.aishu.spring_security.Dto.UserDTO;
import com.aishu.spring_security.Repository.UserRepo;
import com.aishu.spring_security.dao.UserPrinciple;
import com.aishu.spring_security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private UserRepo userRepo;

    @ModelAttribute("currentUser")
    public UserDTO addUserDetails(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Case 1: Local DB user
            if (principal instanceof UserPrinciple userPrincipal) {
                User user = userRepo.findByUsername(userPrincipal.getUsername())
                        .orElse(null);
                if (user != null) {
                    String firstLetter = user.getUsername() != null && !user.getUsername().isEmpty()
                            ? user.getUsername().substring(0, 1).toUpperCase()
                            : "?";
                    UserDTO dto = new UserDTO(user.getFirstName(), user.getUsername(), null, firstLetter);
                    model.addAttribute("firstLetter", firstLetter);
                    return dto;
                }
            }

            // Case 2: OAuth2 user (Google, GitHub, etc.)
            if (principal instanceof OAuth2User oauthUser) {
                String name = oauthUser.getAttribute("name");
                if (name == null || name.isBlank()) {
                    name = oauthUser.getAttribute("login");
                }
                String email = oauthUser.getAttribute("email");
                String picture = oauthUser.getAttribute("picture");
                if (picture == null || picture.isBlank()) {
                    picture = oauthUser.getAttribute("avatar_url");
                }

                String firstLetter = (name != null && !name.isEmpty())
                        ? name.substring(0, 1).toUpperCase()
                        : "?";

                UserDTO dto = new UserDTO(name, email, picture, firstLetter);
                model.addAttribute("firstLetter", firstLetter);
                return dto;
            }
        }
        return null;
    }
}
