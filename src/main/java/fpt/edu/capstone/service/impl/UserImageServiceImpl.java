//package fpt.edu.capstone.service.impl;
//
//import java.util.Arrays;
//
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class UserImageServiceImpl {
//    //type = IMG || CV
//    private boolean isValidFile(MultipartFile file, String type){
//        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//        if(type.equals("IMG")) {
//                return  Arrays.asList(new String[] {"jpeg","jpg","png"}).contains(fileExtension.toLowerCase()) ;
//        }
//        return  Arrays.asList(new String[] {"pdf"}).contains(fileExtension.toLowerCase()) ;
//    }
//}
