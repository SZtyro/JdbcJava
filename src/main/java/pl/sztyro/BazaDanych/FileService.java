package pl.sztyro.BazaDanych;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:4200")
@Component
public class FileService {

    List<File> filesInFolder;

    private static final String FILE_DIRECTORY = "src/files";

    public void storeFile(MultipartFile file) throws IOException {
        Path filePath = Paths.get(FILE_DIRECTORY + "/" + file.getOriginalFilename());

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(Files.list(Paths.get(FILE_DIRECTORY)));



        filesInFolder = Files.walk(Paths.get(FILE_DIRECTORY))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        System.out.println(filesInFolder);


    }


}
