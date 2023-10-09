package software.darkmatter.school.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import software.darkmatter.school.blog.config.security.CustomAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebSecurityCustomizer {

    private final CustomAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(
                requestMatcherRegistry -> requestMatcherRegistry.anyRequest().authenticated()
            )
            .logout(LogoutConfigurer::permitAll)
            .sessionManagement(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Override
    public void customize(WebSecurity web) {
        web.httpFirewall(new DefaultHttpFirewall());
    }
}
