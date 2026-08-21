package com.aishu.spring_security.controller;

import com.aishu.spring_security.dao.UserPrinciple;
import com.aishu.spring_security.model.User;
import com.aishu.spring_security.service.CookieUtil;
import com.aishu.spring_security.service.JwtService;
import com.aishu.spring_security.service.JwtStoreService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;




import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class authController {

    @Autowired
    JwtService jwtService;
    @Autowired
    JwtStoreService jwtStoreService;

    // ---- Login ----
    @GetMapping("/login")
    public String login(Model model) throws IOException {
        // Load country prefix JSON from Samayo
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> countries = mapper.readValue(
                new URL("https://raw.githubusercontent.com/samayo/country-json/master/src/country-by-calling-code.json"),
                new TypeReference<List<Map<String, Object>>>() {});
        model.addAttribute("countries", countries);
        model.addAttribute("user", new User()); // must match th:object="${user}"
        return "tw-login";
    }
    @GetMapping("/LoginErr")
    public String LoginErr(Model model) {
        model.addAttribute("user", new User());
        return "testwheel-404-error-page";
    }

    // ---- Register ----
    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "tw-signup";
    }

@GetMapping("/dashboard")
public String profile(HttpServletRequest request,Model model, Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrinciple userDetails) {
            String username = userDetails.getUsername();

            model.addAttribute("email", userDetails.getUsername()); // safer if available

            // Safe first letter
            String firstLetter = (username != null && !username.isEmpty())
                    ? username.substring(0, 1)
                    : "?";

            model.addAttribute("firstLetter", firstLetter);

            return "dashboard";
        }

        if (principal instanceof DefaultOAuth2User oauthUser) {
            //OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            //Map<String, Object> oauthDetails = new HashMap<>();

            String oauthId = oauthUser.getAttribute("sub");
            String name    = oauthUser.getAttribute("name");
            String email   = oauthUser.getAttribute("email");
            String picture = oauthUser.getAttribute("picture");

            model.addAttribute("name", name != null ? name : "Unknown User");
            model.addAttribute("email", email != null ? email : "No Email");
            model.addAttribute("picture", picture);

            return "dashboard";
        }
    }

    return "redirect:/login"; // fallback if not authenticated
}





    // ---- Logout (redirect) ----
    @GetMapping("/perform_logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        Cookie refreshCookie = CookieUtil.getCookie(request, "refreshToken");
        if (refreshCookie != null) {
            String refreshToken = refreshCookie.getValue();
            try {
                String username = jwtService.extractUsername(refreshToken);
                if (username != null) {
                    jwtStoreService.revokeAllTokensForUser(username);
                }
            } catch (Exception e) {
                // ignore if token invalid
            }
        }
        // Clear cookies
        response.addCookie(CookieUtil.deleteCookie("accessToken"));
        response.addCookie(CookieUtil.deleteCookie("refreshToken"));
        response.addCookie(CookieUtil.deleteCookie("jwt"));

        // Show logout page
        model.addAttribute("message", "You have been logged out successfully.");
        return "redirect:/login?logout";
    }
}
