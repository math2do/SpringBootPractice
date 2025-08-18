package in.math2do.practice.service;

import in.math2do.practice.constants.Constant;
import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.enums.Role;
import in.math2do.practice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
  @Autowired
  private UserRepository repository;
  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  private RedisService redis;

  public UserEntity saveUser(UserEntity user) {
    user.setPassword(this.encoder.encode(user.getPassword()));
    user.setRoles(List.of(Role.USER.getRoleName()));
    return this.repository.save(user);
  }

  public UserEntity updateUser(UserEntity user, UserEntity userReq) {
    // TODO: conditionally set the fields for which updated values are sent
    // Here we assume that new password, new username, are sent as part of update request
    user.setUsername(user.getUsername());
    user.setPassword(this.encoder.encode(userReq.getPassword()));
    return this.repository.save(user);
  }

  public List<UserEntity> getAllUsers() {
    List<UserEntity> users = this.repository.findAll();
    redis.set(Constant.ALL_USERS_KEY, users, Constant.ALL_USERS_EXP);
    return users;
  }

  public Optional<UserEntity> getUserById(ObjectId id) {
    log.info("userId received: {}", id);
    return this.repository.findById(id);
  }

  public void deleteById(ObjectId id) {
    this.repository.deleteById(id);
  }

  public Optional<UserEntity> findByUsername(String username) {
    return this.repository.findByUsername(username);
  }
}
