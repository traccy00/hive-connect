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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AmazonS3ClientService {

    private static final Logger logger = LoggerFactory.getLogger(AmazonS3ClientService.class);

    private static final List<String> VALID_FILE_TYPES = new ArrayList<String>(3);

    static {
        VALID_FILE_TYPES.add("image/jpg");
        VALID_FILE_TYPES.add("image/jpeg");
        VALID_FILE_TYPES.add("image/png");
        VALID_FILE_TYPES.add("image/bmp");
    }

    private int MAX_FILE_SIZE = 5242880;

    public String uploadFileAmazonS3(UploadFileRequest request, MultipartFile multipartFile) throws Exception {
        String fileType = multipartFile.getContentType().split("/")[1];
        String fileName = UUID.randomUUID().toString().toUpperCase() + "." + fileType;

        logger.info("file-type request:" + fileType);
//        if (!VALID_FILE_TYPES.contains(multipartFile.getContentType())) {
//            logger.info("uploadAvatar - Wrong type");
//            throw new HiveConnectException(ResponseMessageConstants.UPLOAD_IMAGE_WRONG_TYPE);
//        }

        logger.info("file-size request:" + multipartFile.getSize());
        if (multipartFile.getSize() > MAX_FILE_SIZE) {
            logger.info("uploadAvatar - MAX_FILE_SIZE");
            throw new HiveConnectException(ResponseMessageConstants.UPLOAD_IMAGE_OVER_SIZE);
        }

        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAXYJP2KLRA46NTLAN",
                "RlosMR/wqvzZ/eNWCUgU1bw3EAJuZx46kXzkeg1Z"
        );
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1)
                .build();
        String bucketName = "hive-connect-images";
//        List<Bucket> buckets = s3client.listBuckets();
//        for (Bucket bucket : buckets) {
//            System.out.println(bucket.getName());
//        }
        File file = convertMultiPartToFile(multipartFile);
        s3client.putObject(bucketName,"hiveconnect/" + fileName, file);
        return fileName;
    }

    public String uploadFile(UploadFileRequest request, MultipartFile multipartFile) throws Exception {
        String fileName = uploadFileAmazonS3(request, multipartFile);
        return ResponseMessageConstants.AMAZON_SAVE_URL + fileName;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }
}
