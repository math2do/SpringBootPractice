package in.math2do.practice.enums;

import lombok.Getter;

@Getter
public enum Role {
  ADMIN("ADMIN"), USER("USER"), MANAGER("MANAGER"), GUEST("GUEST");

  private final String roleName;

  Role(String roleName) {
    this.roleName = roleName;
  }

}
