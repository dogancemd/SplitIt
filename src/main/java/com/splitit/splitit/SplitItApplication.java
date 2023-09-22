package com.splitit.splitit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableMongoRepositories
public class SplitItApplication {


    public static void main(String[] args) {

        SpringApplication.run(SplitItApplication.class, args);
    }

}
