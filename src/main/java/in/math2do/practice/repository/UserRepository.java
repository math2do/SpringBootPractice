package in.math2do.practice.repository;

import in.math2do.practice.entity.UserEntity;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
  public Optional<UserEntity> findByUsername(String username);
}
