package tr.com.huseyinaydin.user.validation;

import org.springframework.beans.factory.annotation.Autowired;
import tr.com.huseyinaydin.file.FileService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

    @Autowired
    private FileService fileService;

    private String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty())
            return true;
        String type = fileService.detectType(value);
        // if(type.equals("image/jpeg") || type.equals("image/png")) return true;
        for (String validType : types) {
            if (type.contains(validType))
                return true;
        }
        String validTypes = Arrays.stream(types).collect(Collectors.joining(", "));

        context.disableDefaultConstraintViolation();
        /*
         Varsayılan hata mesajını devre dışı bırakır. Yani, normalde Hibernate’in döndüreceği varsayılan hata mesajını kullanmaz ve bunun yerine özel bir mesaj göstermek isteriz.
         */

        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context
                .unwrap(HibernateConstraintValidatorContext.class);
        /*
         ConstraintValidatorContext nesnesini HibernateConstraintValidatorContext sınıfına dönüştürür. Bu dönüşüm, Hibernate'e özgü ek özelliklere (mesaj parametreleri ekleme gibi) erişmek için yapılır.
         */

        hibernateConstraintValidatorContext.addMessageParameter("types", validTypes);
        /*
         Hata mesajına "types" adlı bir parametre ekler. Bu parametre, geçerli dosya tiplerini içeren validTypes değişkenidir. Böylece hata mesajında bu değer dinamik olarak yer alabilir.
         */

        hibernateConstraintValidatorContext
                .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addConstraintViolation();
        /*
         Varsayılan hata mesajı şablonunu kullanarak yeni bir hata mesajı oluşturur. Ancak, bu mesaj özelleştirilmiştir çünkü ek parametreler (örneğin geçerli dosya tipleri) dahil edilmiştir.    
         .addConstraintViolation(): Yeni hata mesajını oluşturur ve Hibernate'in validasyon mekanizmasına ekler. Bu mesaj, geçerli dosya tiplerini içerir ve kullanıcının hangi dosya tiplerinin geçerli olduğunu görmesini sağlar.
         */
        return false;
    }
}