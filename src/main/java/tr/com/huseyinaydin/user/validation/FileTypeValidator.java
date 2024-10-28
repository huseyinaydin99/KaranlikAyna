package tr.com.huseyinaydin.user.validation;

import org.springframework.beans.factory.annotation.Autowired;
import tr.com.huseyinaydin.file.FileService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

    @Autowired
    private FileService fileService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) return true;
        String type = fileService.detectType(value);
        if(type.equals("image/jpeg") || type.equals("image/png")) return true;
        return false;
    }
}