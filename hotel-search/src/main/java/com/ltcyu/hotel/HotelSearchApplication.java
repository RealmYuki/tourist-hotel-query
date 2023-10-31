package com.ltcyu.hotel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ltcyu.hotel.mapper")
@SpringBootApplication
public class HotelSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelSearchApplication.class, args);
    }

}
