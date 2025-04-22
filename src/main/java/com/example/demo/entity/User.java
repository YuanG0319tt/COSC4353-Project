package com.example.demo.entity;

import lombok.Data;

@Data
public class User {
    Integer id;
    String email;
    String password;
    String role;
}
