package tr.com.huseyinaydin.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordUpdate(
  @Size(min=8, max=255)
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{KaranlikAyna.constraint.password.pattern}")
  String password) {
}