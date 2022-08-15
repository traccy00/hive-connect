package fpt.edu.capstone.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import fpt.edu.capstone.dto.UploadFileRequest;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.utils.Enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class AmazonS3ClientService {

    private static final Logger logger = LoggerFactory.getLogger(AmazonS3ClientService.class);

    private  static final String[] IMAGE_TYPES = new String[]{"image/jpg","image/jpeg","image/png","image/bmp"};
    private static final String[] CV_TYPES = new String[]{
            "application/pdf",
            "application/msword",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    };
    private static final Map<Enums.FileUploadType,List<String>> TYPE_VALIDATORS = new HashMap<Enums.FileUploadType, List<String>>() {{
        put(Enums.FileUploadType.Image, Arrays.asList(IMAGE_TYPES));
        put(Enums.FileUploadType.CV, Arrays.asList(CV_TYPES));
    }};
    private long MAX_FILE_SIZE = 5242880;

    public String uploadFileAmazonS3(UploadFileRequest request, MultipartFile multipartFile) throws Exception {
        if(multipartFile == null) {
            throw new HiveConnectException(ResponseMessageConstants.CHOOSE_UPLOAD_FILE);
        }
        String fileType = multipartFile.getContentType().split("/")[1];
        String fileName = UUID.randomUUID().toString().toUpperCase() + "." + fileType;

        logger.info("file-type request:" + fileType);
//        if (!TYPE_VALIDATORS.get(Enums.FileUploadType.parse(request.getTypeUpload())).contains(multipartFile.getContentType())) {
//            logger.info("uploadImage - Wrong type");
//            throw new HiveConnectException(ResponseMessageConstants.UPLOAD_IMAGE_WRONG_TYPE);
//        }
//        logger.info("file-size request:" + request.getFile().getSize());
//        if (request.getFile().getSize() > MAX_FILE_SIZE) {
//            logger.info("uploadImage - MAX_FILE_SIZE");
//            throw new HiveConnectException(ResponseMessageConstants.UPLOAD_IMAGE_OVER_SIZE);
//        }

        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAXMAOZRCCFMZHVLUM",
                "tOxI5PsbNCq9EMAnzEEPUeBH8cydy9KECRGCIYqT"
        );
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
        String bucketName = "hive-connect-social";
        List<Bucket> buckets = s3client.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
        File file = convertMultiPartToFile(multipartFile, fileName);
        s3client.putObject(bucketName,"hiveconnect/" + fileName, file);
        System.out.println("Amazon3=========  "+ fileName);
        return fileName;
    }

    public String uploadFile(UploadFileRequest request, MultipartFile multipartFile) throws Exception {
        String fileName = uploadFileAmazonS3(request, multipartFile);
        return ResponseMessageConstants.AMAZON_SAVE_URL + fileName;
    }

    private File convertMultiPartToFile(MultipartFile file, String fileName) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        file.transferTo(convFile);
        return convFile;
    }
}
