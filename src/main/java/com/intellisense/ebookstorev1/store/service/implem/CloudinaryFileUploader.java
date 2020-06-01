package com.intellisense.ebookstorev1.store.service.implem;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.intellisense.ebookstorev1.store.exception.APPException;
import com.intellisense.ebookstorev1.store.prop.AppProp;
import com.intellisense.ebookstorev1.store.service.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service("cloudinary")
public class CloudinaryFileUploader implements FileUploader {
    @Autowired
    Cloudinary cloudinary;

    @Autowired
    AppProp prop;

    @Override
    public Map upload(MultipartFile file) {
        String id = System.currentTimeMillis() +"_"+ file.getName();
        Map params = ObjectUtils.asMap(
                "public_id", prop.CLOUDINARY_UPLOAD_PATH + "/" + id,
                "overwrite", true,
                "resource_type", "auto"
        );

        Map res = null;
        try {
            res = cloudinary.uploader().upload(file.getBytes(), params);
        } catch (IOException e) {
            throw new APPException(e.getMessage());
        }
        return res;
    }

    @Override
    public void validate(MultipartFile file) {

    }
}