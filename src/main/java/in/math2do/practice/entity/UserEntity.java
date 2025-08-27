package in.math2do.practice.entity;

import java.util.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;

  @Indexed(unique = true)
  @NonNull
  private String username;
  @NonNull
  private String password;
  @NonNull
  private List<String> roles;

  @DBRef(lazy = true) // avoid loading the User document unless needed.
  @Builder.Default
  private List<JournalEntry> journals = new ArrayList<>();
}
