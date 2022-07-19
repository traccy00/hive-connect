package fpt.edu.capstone.service.impl;

import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserImageService {


    //type = IMG || CV
    private boolean isValidFile(MultipartFile file, String type){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(type.equals("IMG")) {
                return  Arrays.asList(new String[] {"jpeg","jpg","png"}).contains(fileExtension.toLowerCase()) ;
        }
        return  Arrays.asList(new String[] {"pdf"}).contains(fileExtension.toLowerCase()) ;
    }

//    public Avatar save(MultipartFile file, String type, long userId) throws IOException {
//        if(file.isEmpty()){
//            throw new RuntimeException("Failed to store empty file");
//        }
//        if(!isValidFile(file, type)){
//            throw new RuntimeException("File is invalid");
//        }
//        float fileSizeInMB = file.getSize() / 1_000_000;
//        if (fileSizeInMB > 25.0f){
//            throw new RuntimeException("File must  be <= 25mb");
//        }
//        Avatar avatar = new Avatar();
//        avatar.setName(StringUtils.cleanPath(file.getOriginalFilename()));
//        avatar.setContentType(file.getContentType());
//        avatar.setData(file.getBytes());
//        avatar.setSize(file.getSize());
//        avatar.setUserId(userId);
//
//       return fileRepository.save(avatar);
//    }

//    public Avatar saveFile(MultipartFile file) throws IOException {
//        Avatar avatar = new Avatar();
//        avatar.setName(StringUtils.cleanPath(file.getOriginalFilename()));
//        avatar.setContentType(file.getContentType());
//        avatar.setData(file.getBytes());
//        avatar.setSize(file.getSize());
//
//        return fileRepository.save(avatar);
//    }

//    public Optional<Avatar> getFile(String id) {
//        return fileRepository.findById(id);
//    }
//
//    public List<Avatar> getAllFiles() {
//        return fileRepository.findAll();
//    }
//
//    public Optional<Avatar> findAvatarByUserId(long userId){
//        return fileRepository.findAvatarByUserId(userId);
//    };
//
//    public void updateAvatar(String id, MultipartFile file) throws IOException {
//        fileRepository.updateAvatar(file.getBytes(), id);
//    }
}
