package in.math2do.practice.controller;

import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService service;

  @Autowired
  private PasswordEncoder encoder;

  @GetMapping
  public List<UserEntity> getAllUsers() {
    return this.service.getAllUsers();
  }

  @PostMapping
  public void createUser(@RequestBody UserEntity user) {
    user.setPassword(this.encoder.encode(user.getPassword()));
    this.service.saveUser(user);
  }

  @PutMapping("/{username}")
  public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserEntity user) {
    UserEntity userInDb = this.service.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    ;
    if (userInDb != null) {
      userInDb.setUsername(user.getUsername());
      userInDb.setPassword(encoder.encode(user.getPassword()));
      service.saveUser(userInDb);
    }

    return ResponseEntity.noContent().build();
  }

}
