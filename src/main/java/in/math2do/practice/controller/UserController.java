package in.math2do.practice.controller;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService service;

  @GetMapping
  public List<UserEntity> getAllUsers() {
    return service.getAllUsers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserEntity> getUserById(@PathVariable ObjectId id) {
    UserEntity user =
        service.getUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return ResponseEntity.ok().body(user);
  }

  @PostMapping
  public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
    UserEntity savedUser = service.saveUser(user);
    return ResponseEntity.ok().body(savedUser);
  }

  @PutMapping("/{username}")
  public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserEntity user) {
    UserEntity userInDb = service.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    if (userInDb != null) {
      UserEntity updatedUser = service.updateUser(userInDb, user);
      return ResponseEntity.ok().body(updatedUser);
    }

    return ResponseEntity.noContent().build();
  }

}
