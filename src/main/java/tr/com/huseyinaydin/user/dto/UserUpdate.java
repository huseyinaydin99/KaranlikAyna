package tr.com.huseyinaydin.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import tr.com.huseyinaydin.user.validation.FileType;

public record UserUpdate(
    @NotBlank(message = "{KaranlikAyna.constraint.username.notblank}")
    @Size(min = 4, max = 255)
    String username,

    @FileType(types = {"jpeg", "png"})
    String image
) {
    
}