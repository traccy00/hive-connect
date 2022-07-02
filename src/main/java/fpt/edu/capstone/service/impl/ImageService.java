package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.repository.ImageRepository;
import fpt.edu.capstone.repository.UserImageRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;


    //type = IMG || CV
    private boolean isValidFile(MultipartFile file, String type){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(type.equals("IMG")) {
            return  Arrays.asList(new String[] {"jpeg","jpg","png"}).contains(fileExtension.toLowerCase()) ;
        }
        return  Arrays.asList(new String[] {"pdf"}).contains(fileExtension.toLowerCase()) ;
    }

    public Image saveCompanyAvatar(MultipartFile file, String type, long companyId) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("Failed to store empty file");
        }
        if(!isValidFile(file, type)){
            throw new RuntimeException("File is invalid");
        }
        float fileSizeInMB = file.getSize() / 1_000_000;
        if (fileSizeInMB > 25.0f){
            throw new RuntimeException("File must  be <= 25mb");
        }
        Image image = new Image();
        image.setAvatar(true);
        image.setCompanyId(companyId);
        image.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        image.setBanner(false);
        image.setContentType(file.getContentType());
        image.setData(file.getBytes());

        return imageRepository.save(image);
    }

    public Optional<Image> getFile(String id) {
        return imageRepository.findById(id);
    }

    public List<Image> getAllFiles() {
        return imageRepository.findAll();
    }

    public Optional<Image> findAvatarByCompanyId(long companyId){
        return imageRepository.findAvatarByCompanyId(companyId);
    };

    public void updateAvatar(String id, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("Failed to store empty file");
        }
        if(!isValidFile(file, "IMG")){
            throw new RuntimeException("File is invalid");
        }
        float fileSizeInMB = file.getSize() / 1_000_000;
        if (fileSizeInMB > 25.0f){
            throw new RuntimeException("File must  be <= 25mb");
        }
        imageRepository.updateCompanyAvatar(file.getBytes(),true,id);
    }

}
