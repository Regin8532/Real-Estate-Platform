package com.example.realestate.service.impl;

import com.cloudinary.Cloudinary;
import com.example.realestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file) {
        try {
            Map upload = cloudinary.uploader()
                    .upload(file.getBytes(), Map.of());
            return upload.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Image upload failed");
        }
    }
}
