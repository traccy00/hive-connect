package fpt.edu.capstone.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import fpt.edu.capstone.dto.File.FileResponse;
import fpt.edu.capstone.dto.File.ImageRequest;
import fpt.edu.capstone.dto.UploadFileRequest;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.Avatar;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.service.impl.ImageService;
import fpt.edu.capstone.service.impl.UserImageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ImageService imageService;

    @PostMapping("/import-file")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            userImageService.saveFile(file);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping("/get")
    public List<FileResponse> list() {
        return userImageService.getAllFiles()
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private FileResponse mapToFileResponse(Avatar avatar) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(avatar.getId())
                .toUriString();
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(avatar.getId());
        fileResponse.setName(avatar.getName());
        fileResponse.setContentType(avatar.getContentType());
        fileResponse.setSize(avatar.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<Avatar> fileEntityOptional = userImageService.getFile(id);

        if (!fileEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        Avatar avatar = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatar.getName() + "\"")
                .contentType(MediaType.valueOf(avatar.getContentType()))
                .body(avatar.getData());
    }


    @PostMapping("/upload-image")
    public ResponseData uploadImage(@RequestPart ImageRequest imageRequest, @RequestPart("file") MultipartFile file) {
        try {
            if (imageRequest.getType().toLowerCase().equals("avatar")) { //if is image avatar
                Optional<Users> users = userService.findByIdOp(imageRequest.getUserId());
                if (users.isPresent()) { //Check if user is existed
                    Optional<Avatar> avatarImgSearched = userImageService.findAvatarByUserId(imageRequest.getUserId());
                    if (avatarImgSearched.isPresent()) {
                        userImageService.updateAvatar(avatarImgSearched.get().getId(), file);
                        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Update avatar successful", avatarImgSearched.get().getId());
                    } else {
                        Avatar avatar = userImageService.save(file, "IMG", imageRequest.getUserId());
                        userService.updateAvatarUrl(avatar.getId(), imageRequest.getUserId());
                        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Update avatar successful", avatar.getId());
                    }
                }
                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "Can not find this user", imageRequest.getUserId());
            } else { //id company image
                Optional<Company> companySearched = companyService.findById(imageRequest.getCompanyId());
                if (companySearched.isPresent()) {
                    Optional<Image> imageSearched = imageService.findAvatarByCompanyId(imageRequest.getCompanyId());
                    if (imageSearched.isPresent()) {
                        imageService.updateAvatar(imageSearched.get().getId(), file);
                        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "update avatar successful", imageSearched.get().getId());
                    } else {
                        Image image = imageService.saveCompanyAvatar(file, "IMG", imageRequest.getCompanyId());
                        companyService.updateCompanyAvatarUrl(image.getId(), image.getCompanyId());
                        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Insert avatar successful", image.getId());
                    }
                } else {
                    return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Can not find this company", imageRequest.getCompanyId());
                }
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/get-image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {

        Optional<Avatar> fileEntityOptional = userImageService.getFile(id);
        Optional<Image> image = imageService.finById(id);

        if (fileEntityOptional.isPresent()) {
            Avatar avatar = fileEntityOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatar.getName() + "\"")
                    .contentType(MediaType.valueOf(avatar.getContentType()))
                    .body(avatar.getData());
        }

        if (image.isPresent()) {
            Image image1 = image.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image1.getName() + "\"")
                    .contentType(MediaType.valueOf(image1.getContentType()))
                    .body(image1.getData());
        }

        return ResponseEntity.notFound()
                .build();
    }

//    @PostMapping("/upload-file")
//    public ResponseData uploadImages(@RequestParam String path, @RequestParam String uploadFileName) {
//        try {
//            AWSCredentials credentials = new BasicAWSCredentials(
//                    "AKIAXYJP2KLRA46NTLAN",
//                    "RlosMR/wqvzZ/eNWCUgU1bw3EAJuZx46kXzkeg1Z"
//            );
//            AmazonS3 s3client = AmazonS3ClientBuilder
//                    .standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                    .withRegion(Regions.US_WEST_1)
//                    .build();
//            String bucketName = "hive-connect-images";
//
////            if (s3client.doesBucketExist(bucketName)) {
////                logger.info("Bucket name is not available."
////                        + " Try again with a different Bucket name.");
////                s3client.createBucket(bucketName);
//////                throw new HiveConnectException("Try contact admin, bucket does not exist");
////            }
//            List<Bucket> buckets = s3client.listBuckets();
//            for (Bucket bucket : buckets) {
//                System.out.println(bucket.getName());
//            }
//
//            s3client.putObject(
//                    bucketName,
//                    "E:/test2.txt",
//                    new File("D:/test2.txt")
//            );
//
//            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
//        } catch (Exception e) {
//            String msg = LogUtils.printLogStackTrace(e);
//            logger.error(msg);
//            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
//        }
//    }

    @PostMapping("/upload-file")
    public ResponseData uploadImages(@RequestBody UploadFileRequest request) {
        try {


            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

}
