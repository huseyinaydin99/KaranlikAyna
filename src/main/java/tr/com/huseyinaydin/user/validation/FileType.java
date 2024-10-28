package tr.com.huseyinaydin.user.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FileTypeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileType {
    String message() default "JPG veya PNG olmak zorundadır.";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}