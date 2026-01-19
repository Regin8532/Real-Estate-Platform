package com.example.realestate.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.realestate.service.impl.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void upload_success() throws Exception {
        // Arrange
        when(cloudinary.uploader()).thenReturn(uploader);
        when(multipartFile.getBytes()).thenReturn("image-data".getBytes());

        Map<String, Object> response = Map.of(
                "secure_url", "https://cloudinary.com/test.jpg"
        );

        when(uploader.upload(any(byte[].class), anyMap()))
                .thenReturn(response);

        // Act
        String url = imageService.upload(multipartFile);

        // Assert
        assertEquals("https://cloudinary.com/test.jpg", url);
        verify(uploader, times(1))
                .upload(any(byte[].class), anyMap());
    }

    @Test
    void upload_failure() throws Exception {
        // Arrange
        when(cloudinary.uploader()).thenReturn(uploader);
        when(multipartFile.getBytes()).thenThrow(new RuntimeException("IO error"));

        // Act + Assert
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> imageService.upload(multipartFile)
        );

        assertEquals("Image upload failed", ex.getMessage());
    }
}
