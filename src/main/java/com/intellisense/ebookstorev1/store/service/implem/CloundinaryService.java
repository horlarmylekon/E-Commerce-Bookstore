package com.intellisense.ebookstorev1.store.service.implem;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellisense.ebookstorev1.store.exception.APPException;
import com.intellisense.ebookstorev1.store.model.CloudinaryResponse;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloundinaryService {

    @Value("${com.cloudinary.cloud_name}")
    String mCloudName;

    @Value("${com.cloudinary.api_key}")
    String mApiKey;

    @Value("${com.cloudinary.api_secret}")
    String mApiSecret;

    public CloudinaryResponse uploadImage(MultipartFile multipartFile, String folder){
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);

        try{
            File f = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
            multipartFile.transferTo(f);

            Map response = c.uploader().upload(f, ObjectUtils.asMap("folder", folder));

            JSONObject json = new JSONObject(response);
            String url = json.getString("url");
            System.out.println("Blow " + json.toString());
            System.out.println("For " + url);
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(json.toString(),CloudinaryResponse.class);
        }catch (Exception e){
            throw new APPException(e.getMessage());
        }
    }

    public List<CloudinaryResponse> getFinals() {
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
        List<CloudinaryResponse> retval = new ArrayList<CloudinaryResponse>();
        try {
            Map response = c.api().resource("", ObjectUtils.asMap("type", "upload"));
            JSONObject json = new JSONObject(response);
            JSONArray ja = json.getJSONArray("resources");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject j = ja.getJSONObject(i);
                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println(j.toString());
                retval.add(objectMapper.readValue(j.toString(), CloudinaryResponse.class));
            }

            return retval;
        } catch (Exception e) {
            throw new APPException(e.getMessage());
        }
    }

    public void delete(Iterable<String> publicId) {
        try {

            Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
            c.api().deleteResources(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new APPException(e.getMessage());
        }
    }
}
