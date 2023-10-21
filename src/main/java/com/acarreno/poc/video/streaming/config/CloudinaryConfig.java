package com.acarreno.poc.video.streaming.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

  @Bean
  Uploader uploader() {
    return new Cloudinary(ObjectUtils.asMap("cloud_name", "dc56f2npy", "api_key", "166373349712132",
        "api_secret", "wQzD4nIg9rJgm8ChTfyuNjhC2rA")).uploader();
  }

}
