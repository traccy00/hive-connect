package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.service.CommonService;
import org.springframework.web.multipart.MultipartFile;

public class CommonServiceImpl implements CommonService {
    @Override
    public String uploadImage(long userId, MultipartFile multipartFile) throws Exception {
//        String fileType = multipartFile.getContentType().split("/")[1];
//
//        logger.info("file-type request:" + fileType);
//        if (!VALID_FILE_TYPES.contains(multipartFile.getContentType())) {
//            logger.info("uploadAvatar - Wrong type");
//            throw new BusinessException(ResponseMessageConstants.UPLOAD_IMAGE_WRONG_TYPE);
//        }
//
//        logger.info("file-size request:" + multipartFile.getSize());
//        if (multipartFile.getSize() > MAX_FILE_SIZE) {
//            logger.info("uploadAvatar - MAX_FILE_SIZE");
//            throw new BusinessException(ResponseMessageConstants.UPLOAD_IMAGE_OVER_SIZE);
//        }
//
//        // UPLOAD IMAGE TO S3 AND RESULT URL BACK
//        String fileName = this.amazonS3ClientService.awsS3UploadByMultiPartFile(multipartFile, fileType, false);
//        logger.info("Upload success {}", fileName);
//        //this log activity
//        this.logActivity(userId, ActivityActionConstants.UPLOAD_IMAGE, fileName);
//        return fileName;
        return null;
    }
}
