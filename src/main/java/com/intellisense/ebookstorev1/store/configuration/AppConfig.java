package com.intellisense.ebookstorev1.store.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.intellisense.ebookstorev1.store.prop.AppProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class AppConfig {

    @Autowired
    AppProp prop;

    //Create Beans here
    @Bean
    public Cloudinary cloudinary(){
        Map ConfigMap = ObjectUtils.asMap(
            "cloud_name", prop.CLOUDINARY_CLOUD_NAME,
                "api_key", prop.CLOUDINARY_API_KEY,
                "api_secret", prop.CLOUDINARY_API_SECRET
        );

        return new Cloudinary(ConfigMap);
    }
}
