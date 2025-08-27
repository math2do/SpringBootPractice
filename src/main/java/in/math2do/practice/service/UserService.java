package in.math2do.practice.service;

import java.util.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import in.math2do.practice.constants.Constant;
import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.enums.Role;
import in.math2do.practice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
  private UserRepository repository;
  private PasswordEncoder encoder;
  private RedisService redis;

  @Autowired
  public UserService(UserRepository repository, PasswordEncoder encoder, RedisService redis) {
    this.repository = repository;
    this.encoder = encoder;
    this.redis = redis;
  }

  public UserEntity saveUser(UserEntity user) {
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRoles(List.of(Role.USER.getRoleName()));
    return repository.save(user);
  }

  public UserEntity updateUser(UserEntity user, UserEntity userReq) {
    // TODO: conditionally set the fields for which updated values are sent
    // Here we assume that new password, new username, are sent as part of update request
    user.setPassword(encoder.encode(userReq.getPassword()));
    return repository.save(user);
  }

  public List<UserEntity> getAllUsers() {
    List<UserEntity> users = repository.findAll();
    redis.set(Constant.ALL_USERS_KEY, users, Constant.ALL_USERS_EXP);
    return users;
  }

  public Optional<UserEntity> getUserById(ObjectId id) {
    log.info("userId received: {}", id);
    return repository.findById(id);
  }

  public void deleteById(ObjectId id) {
    repository.deleteById(id);
  }

  public Optional<UserEntity> findByUsername(String username) {
    return repository.findByUsername(username);
  }
}
