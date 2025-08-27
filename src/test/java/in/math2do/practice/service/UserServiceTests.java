package in.math2do.practice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.enums.Role;
import in.math2do.practice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private RedisService redis;
  @Spy
  private PasswordEncoder encoder = new BCryptPasswordEncoder();

  @Test
  void testSaveUser() {
    UserEntity user = Mockito.spy(UserEntity.builder().username("username").password("123")
        .roles(List.of(Role.USER.getRoleName())).build());

    // create the user to be created in db
    String encodedPassword = encoder.encode(user.getPassword());
    UserEntity mockDbUser = UserEntity.builder().username(user.getUsername())
        .password(encodedPassword).roles(user.getRoles()).build();

    when(userRepository.save(any(UserEntity.class))).thenReturn(mockDbUser);
    when(encoder.encode(user.getPassword())).thenReturn(encodedPassword);

    UserEntity savedUser = userService.saveUser(user);

    // user will be set with encoded password before saving
    verify(user, times(1)).setPassword(argThat(password -> password.equals(encodedPassword)));

    // user will be set default roles before saving
    verify(user, times(1))
        .setRoles(argThat(roles -> roles.equals(List.of(Role.USER.getRoleName()))));

    assertThat(savedUser.getUsername()).isEqualTo("username");
    // match the password
    assertThat(encoder.matches("123", savedUser.getPassword())).isEqualTo(true);
    assertArrayEquals(new String[] {Role.USER.getRoleName()}, savedUser.getRoles().toArray());
  }

  @Test
  void testPasswordMatching() {
    assertThat(encoder.matches("123", encoder.encode("123"))).isTrue();
  }
}
