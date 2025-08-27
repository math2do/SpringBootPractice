package in.math2do.practice.config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import in.math2do.practice.enums.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth

        // public endpoints
        .requestMatchers("/health", "/public/**").permitAll()

        // sign up mustn't require auth
        .requestMatchers(HttpMethod.POST, "/users/**").permitAll()

        .requestMatchers("/users/**").hasAnyRole(Role.ADMIN.getRoleName(), Role.USER.getRoleName())

        .requestMatchers("/journals/**")
        .hasAnyRole(Role.ADMIN.getRoleName(), Role.USER.getRoleName())

        // rest endpoints are permitted
        .anyRequest().permitAll()).httpBasic(withDefaults());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
