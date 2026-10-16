package com.ecommerce.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        ///Find the current file name
        String originalFileName = image.getOriginalFilename();
        /// Generate unique file name
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;
        /// Check the path exists, otherwise create
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdirs();
        /// Upload the image to the server("/api....")
        Files.copy(image.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
