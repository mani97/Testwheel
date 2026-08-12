// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.context.SecurityContextHolder;
// import
// org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

// @Autowired
// JwtService jwtService;

// @Autowired
// CookieUtil cookieUtil;

// public JwtAuthenticationFilter(JwtService jwtService) {
// this.jwtService = jwtService;
// }

// @Override
// protected void doFilterInternal(HttpServletRequest request,
// HttpServletResponse response,
// FilterChain filterChain) throws ServletException, IOException {

// // Extract JWT from cookie
// Cookie jwtCookie = cookieUtil.getCookie(request, "accessToken");
// String token = (jwtCookie != null) ? jwtCookie.getValue() : null;

// if (token != null && jwtService.validateToken(token,
// jwtService.extractUsername(token))) {
// var auth = jwtService.getAuthentication(token);
// auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
// SecurityContextHolder.getContext().setAuthentication(auth);
// }

// filterChain.doFilter(request, response);
// }
// }
