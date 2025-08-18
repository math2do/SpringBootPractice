package in.math2do.practice.service;

import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.enums.Role;
import in.math2do.practice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder encoder;

  @Test
  void testSaveUser() {
    UserEntity mockUser = UserEntity.builder()
      .username("username")
      .password("password")
      .roles(List.of(Role.USER.getRoleName()))
      .build();

    when(userRepository.save(any(UserEntity.class))).thenReturn(mockUser);
    when(encoder.encode(any(String.class))).thenReturn("encoded_password");

    UserEntity savedUser = this.userService.saveUser(mockUser);
    assertThat(savedUser.getUsername()).isEqualTo("username");
    assertThat(savedUser.getPassword()).isEqualTo("encoded_password");
    assertArrayEquals(new String[]{Role.USER.getRoleName()}, savedUser.getRoles().toArray());
  }
}
