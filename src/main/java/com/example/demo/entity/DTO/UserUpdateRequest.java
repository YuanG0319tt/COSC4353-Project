package com.example.demo.entity.DTO;

import lombok.Data;

@Data
public class UserUpdateRequest {
    String email;

    String confirmPassword;

    String oldPassword;
}
