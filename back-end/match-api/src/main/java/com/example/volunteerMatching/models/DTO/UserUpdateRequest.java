package com.example.volunteerMatching.models.DTO;

import lombok.Data;

@Data
public class UserUpdateRequest {
    String email;

    String confirmPassword;

    String oldPassword;
}
