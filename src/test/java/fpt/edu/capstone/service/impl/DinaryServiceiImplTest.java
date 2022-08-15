package fpt.edu.capstone.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DinaryServiceiImplTest {

    @Mock
    private Cloudinary mockCloudinaryConfig;

    private DinaryServiceiImpl dinaryServiceiImplUnderTest;

    @Before
    public void setUp() throws Exception {
        dinaryServiceiImplUnderTest = new DinaryServiceiImpl(mockCloudinaryConfig);
    }

    @Test
    public void testUploadFile() {
        final MultipartFile file = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinaryConfig.uploader()).thenReturn(uploader);
        final String result = dinaryServiceiImplUnderTest.uploadFile(file);
        assertThat(result).isEqualTo("result");
    }
}
