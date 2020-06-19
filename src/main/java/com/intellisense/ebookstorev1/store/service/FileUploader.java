package com.intellisense.ebookstorev1.store.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileUploader {

    Map upload(MultipartFile file);
    Map upload(byte[] file, String name);
    Boolean destroy(String name);
    void validate(MultipartFile file);
}
