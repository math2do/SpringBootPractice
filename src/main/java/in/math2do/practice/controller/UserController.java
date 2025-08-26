package in.math2do.practice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService service;

  @GetMapping
  public List<UserEntity> getAllUsers() {
    return this.service.getAllUsers();
  }

  @PostMapping
  public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
    UserEntity savedUser = this.service.saveUser(user);
    return ResponseEntity.ok().body(savedUser);
  }

  @PutMapping("/{username}")
  public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserEntity user) {
    UserEntity userInDb = this.service.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    if (userInDb != null) {
      UserEntity updatedUser = this.service.updateUser(userInDb, user);
      return ResponseEntity.ok().body(updatedUser);
    }

    return ResponseEntity.noContent().build();
  }

}
