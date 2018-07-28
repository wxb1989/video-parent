package com.video.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VideoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoApiApplication.class, args);
    }
}
