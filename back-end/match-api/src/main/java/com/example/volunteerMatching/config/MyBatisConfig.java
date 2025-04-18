package com.example.volunteerMatching.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.volunteerMatching.mappers")
public class MyBatisConfig {
    // Isolated MyBatis config to prevent context clash
}