package com.giahuy.demo.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, String> uploadImage(MultipartFile file) {
        try {
            Map res = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return res;
        }catch (IOException e) {}

        return null;
    }

    public void deleteImage(String url) {
        CompletableFuture.runAsync(() -> {
            try {
                String[] parts = url.split("/");

                String filename = parts[parts.length - 1];
                String publicId = filename.substring(0, filename.lastIndexOf('.'));

                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}