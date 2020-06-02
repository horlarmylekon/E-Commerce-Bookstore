package com.intellisense.ebookstorev1.store.service.implem;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.intellisense.ebookstorev1.store.exception.APPException;
import com.intellisense.ebookstorev1.store.prop.AppProp;
import com.intellisense.ebookstorev1.store.service.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service("cloudinary")
public class CloudinaryFileUploader implements FileUploader {

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    AppProp prop;

    private Map directByteUpload(byte[] raw, Map params){

        Map res = null;
        try {
            res = cloudinary.uploader().upload(raw, params);
        } catch (IOException e) {
            throw new APPException(e.getMessage());
        }
        return res;
    }


    @Override
    public Map upload(MultipartFile file) {
        String id = System.currentTimeMillis() +"_"+ file.getName();
        Map params = ObjectUtils.asMap(
                "public_id", prop.CLOUDINARY_UPLOAD_PATH + "/" + id,
                "overwrite", true,
                "resource_type", "auto"
        );

        try {
            return directByteUpload(file.getBytes(), params);
        } catch (IOException e) {
            throw new APPException(e.getMessage());
        }
    }

    @Override
    public Map upload(byte[] raw, String name) {
        String id = System.currentTimeMillis() +"_"+ name;
        Map params = ObjectUtils.asMap(
                "public_id", prop.CLOUDINARY_UPLOAD_PATH + "/" + id,
                "overwrite", true,
                "resource_type", "auto"
        );

        return directByteUpload(raw, params);
    }

    @Override
    public Boolean destroy(String publicId) {
        try {
            Map res = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if(String.valueOf(res.get("result")).toLowerCase().equals("ok"))
                return true;
        } catch (IOException e) {
            throw new APPException(e.getMessage());
        }

        return false;
    }

    @Override
    public void validate(MultipartFile file) {

    }
}
