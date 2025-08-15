package in.math2do.practice.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.repository.UserRepository;

@Service
public class CustomUserDetails implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetails(UserRepository repo) {
    this.userRepository = repo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    UserBuilder builder = User.withUsername(user.getUsername());
    builder.password(user.getPassword());
    builder.roles(user.getRole());
    return builder.build();
  }
}
