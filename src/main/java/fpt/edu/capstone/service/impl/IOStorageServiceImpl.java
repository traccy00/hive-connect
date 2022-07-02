package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.service.IOStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

@Service
public class IOStorageServiceImpl implements IOStorageService {
    private final Path storageFolder = Paths.get("upload");
    public IOStorageServiceImpl() {
        try{
            Files.createDirectories(storageFolder);
        }catch (Exception ex){
            throw new RuntimeException("Cannot initialize storage", ex);
        }
    }
    private boolean isValidFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"pdf"}).contains(fileExtension.toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile file) {
        try{
            if(file.isEmpty()){
                throw new RuntimeException("Failed to store empty file");
            }
            if(!isValidFile(file)){
                throw new RuntimeException("File is invalid");
            }
            float fileSizeInMB = file.getSize() / 1_000_000;
            if (fileSizeInMB > 25.0f){
                throw new RuntimeException("File must  be <= 25mb");
            }
            //rename file TO SAVE
            String fileExtension  = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-","");
            generatedFileName = generatedFileName+"."+fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                //Check
                throw new RuntimeException("Cannot store file outside of current directory");
            }
            //Copy file upload to destination file path
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        }catch (IOException ioEx){
            throw new RuntimeException("Failed to store file", ioEx);
        }

    }

    @Override
    public byte[] readFileContent(String fileName) {
        return new byte[0];
    }
}
