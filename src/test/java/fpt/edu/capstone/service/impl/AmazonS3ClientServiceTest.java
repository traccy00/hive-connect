package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.UploadFileRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AmazonS3ClientServiceTest {

    private AmazonS3ClientService amazonS3ClientServiceUnderTest;

    @Before
    public void setUp() throws Exception {
        amazonS3ClientServiceUnderTest = new AmazonS3ClientService();
    }

    @Test
    public void testUploadFileAmazonS3() throws Exception {
        final UploadFileRequest request = new UploadFileRequest();
        request.setType("type");
        request.setUploadFileName("uploadFileName");
        request.setFile(null);
        request.setTypeUpload("typeUpload");

        final MultipartFile file = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
        final String result = amazonS3ClientServiceUnderTest.uploadFileAmazonS3(request, file);
        assertThat(result).isEqualTo("result");
    }

    @Test
    public void testUploadFileAmazonS3_ThrowsException() {
        final UploadFileRequest request = new UploadFileRequest();
        request.setType("type");
        request.setUploadFileName("uploadFileName");
        request.setFile(null);
        request.setTypeUpload("typeUpload");
        final MultipartFile multipartFile = null;
        assertThatThrownBy(
                () -> amazonS3ClientServiceUnderTest.uploadFileAmazonS3(request, multipartFile))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void testUploadFile() throws Exception {
        final UploadFileRequest request = new UploadFileRequest();
        request.setType("type");
        request.setUploadFileName("uploadFileName");
        request.setFile(null);
        request.setTypeUpload("typeUpload");

        final MultipartFile file = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
        final String result = amazonS3ClientServiceUnderTest.uploadFile(request, file);
        assertThat(result).isEqualTo("result");
    }

    @Test
    public void testUploadFile_ThrowsException() {
        final UploadFileRequest request = new UploadFileRequest();
        request.setType("type");
        request.setUploadFileName("uploadFileName");
        request.setFile(null);
        request.setTypeUpload("typeUpload");
        final MultipartFile multipartFile = null;
        assertThatThrownBy(() -> amazonS3ClientServiceUnderTest.uploadFile(request, multipartFile))
                .isInstanceOf(Exception.class);
    }
}
