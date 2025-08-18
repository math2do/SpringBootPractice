package in.math2do.practice.config;

import in.math2do.practice.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/health", "/public/**").permitAll()
        .requestMatchers("/user/**").hasAnyRole(Role.ADMIN.getRoleName(), Role.USER.getRoleName())
        .requestMatchers("/journal/**").hasAnyRole(Role.ADMIN.getRoleName(), Role.USER.getRoleName())

        .anyRequest().authenticated()
      )
      .httpBasic(withDefaults());

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
