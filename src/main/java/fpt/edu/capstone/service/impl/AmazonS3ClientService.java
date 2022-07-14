package fpt.edu.capstone.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import fpt.edu.capstone.dto.UploadFileRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class AmazonS3ClientService {
    public String uploadFile(UploadFileRequest request) {
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
        List<Bucket> buckets = s3client.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
        File file = new File(request.getPath() + request.getUploadFileName());
        s3client.putObject(bucketName,"hiveconnect/" + request.getUploadFileName(), file);
        return request.getUploadFileName();
    }
}
