package org.unam.petrohub_project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Map uploadParams = ObjectUtils.asMap(
                "resource_type", "raw",
                "public_id", fileName
        );

        Map result = cloudinary.uploader().upload(file.getBytes(), uploadParams);

        return result.get("secure_url").toString();
    }
}
