package tr.com.huseyinaydin.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.huseyinaydin.configuration.KaranlikAynaProperties;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tika.Tika;
import java.nio.file.Files;

@Service
public class FileService {

    Tika tika = new Tika();

    @Autowired
    private KaranlikAynaProperties karanlikAynaProperties;

    public String saveBase64StringAsFile(String image) {
        String filename = UUID.randomUUID().toString();
        Path path = getProfileImagePath(filename);
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            /*byte[] base64decoded = Base64.getDecoder().decode(image.split(",")[1]);
            outputStream.write(base64decoded);*/
            outputStream.write(decodedImage(image));
            outputStream.close();
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String detectType(String value) {
        return tika.detect(decodedImage(value)); //dosya uzantısını tespit et.
    }

    private byte[] decodedImage(String encodedImage) {
        return Base64.getDecoder().decode(encodedImage.split(",")[1]);
    }

    public void deleteProfileImage(String image) {
        if(image == null) return;
        Path path = getProfileImagePath(image);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getProfileImagePath(String filename){
        return Paths.get(karanlikAynaProperties.getStorage().getRoot(), karanlikAynaProperties.getStorage().getProfile(), filename);
    }
}