package net.buscacio.travelApi.service.utils;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class UploadFileUtils {

    public String uploadFile(MultipartFile file, String path) throws IOException {
        String fileName = String.format("%s%s", UUID.randomUUID(), Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")));
        file.transferTo(new File(String.format("%s%s", path, fileName)));
        return fileName;
    }
}
