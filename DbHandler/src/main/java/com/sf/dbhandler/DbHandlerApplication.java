package com.sf.dbhandler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sf.dbhandler.mapper")
@SpringBootApplication
public class DbHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbHandlerApplication.class, args);
    }

}
