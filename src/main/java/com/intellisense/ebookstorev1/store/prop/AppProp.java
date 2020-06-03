package com.intellisense.ebookstorev1.store.prop;

//Basic POJO to grab data from app.properties end

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProp {

    @Value("${com.cloudinary.cloud_name}")
    public String CLOUDINARY_CLOUD_NAME;

    @Value("${com.cloudinary.api_key}")
    public String CLOUDINARY_API_KEY;

    @Value("${com.cloudinary.api_secret}")
    public String CLOUDINARY_API_SECRET;

    @Value("${folder.book}")
    public String CLOUDINARY_UPLOAD_PATH;

}
