package software.darkmatter.school.blog.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import software.darkmatter.school.blog.domain.user.business.UserService;
import software.darkmatter.school.blog.domain.user.data.User;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    public static final String PREFIX_USER_ID = "User-Id ";
    public static final String AUTHORIZATION = "Authorization";

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(PREFIX_USER_ID)) {
            String userId = authorizationHeader.substring(PREFIX_USER_ID.length());
            User user = userService.getById(Long.valueOf(userId));
            SecurityContextHolder.getContext().setAuthentication(new SimpleAuthentication(user.getId(), user.getFirstName()));
        }

        filterChain.doFilter(request, response);
    }
}
