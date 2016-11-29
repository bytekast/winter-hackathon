package com.bytekast.hackathon.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ComponentScan("com.bytekast")
@EnableAsync
@EnableScheduling
class MainController {

  public static void main(String[] args) {
    SpringApplication.run(MainController.class, args)
  }
}
