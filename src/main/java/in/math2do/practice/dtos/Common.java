package in.math2do.practice.dtos;

import lombok.*;

public class Common {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ErrorResponse {
    private int status;
    private String path;
    private String message;
  }
}
