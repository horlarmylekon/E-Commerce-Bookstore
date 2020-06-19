package com.intellisense.ebookstorev1.store.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.intellisense.ebookstorev1.store.prop.AppProp;
import com.intellisense.ebookstorev1.store.service.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("cloudinary/")
public class CloudinaryFileUploader {

    @Autowired
//    @Qualifier("cloudinary")
    FileUploader uploaderSvc;

    @PutMapping("upload/book")
    public ResponseEntity<?> uploadFile(@Valid @RequestBody MultipartFile book) throws IOException {

        Map res = uploaderSvc.upload(book);

        String disposableUrl = (String) res.get("url");
        String publicId = (String) res.get("public_id");
        String height = (String) res.get("height");
        String width = (String) res.get("width");
        String type = (String) res.get("format");
        String size = (String) res.get("bytes"); //byte

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
