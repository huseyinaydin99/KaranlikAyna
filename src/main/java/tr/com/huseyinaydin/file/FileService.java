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

@Service
public class FileService {

    @Autowired
    private KaranlikAynaProperties karanlikAynaProperties;

    public String saveBase64StringAsFile(String image) {
        String filename = UUID.randomUUID().toString();
        Path path = Paths.get(karanlikAynaProperties.getStorage().getRoot(), karanlikAynaProperties.getStorage().getProfile(), filename);
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            byte[] base64decoded = Base64.getDecoder().decode(image.split(",")[1]);
            outputStream.write(base64decoded);
            outputStream.close();
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}