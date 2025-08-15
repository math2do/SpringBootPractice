package in.math2do.practice.service;

import in.math2do.practice.entity.UserEntity;
import in.math2do.practice.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;

  public void saveUser(UserEntity user) {
    this.repository.save(user);
  }

  public List<UserEntity> getAllUsers() {
    return this.repository.findAll();
  }

  public Optional<UserEntity> getUserById(ObjectId id) {
    return this.repository.findById(id);
  }

  public void deleteById(ObjectId id) {
    this.repository.deleteById(id);
  }

  public Optional<UserEntity> findByUsername(String username) {
    return this.repository.findByUsername(username);
  }
}
