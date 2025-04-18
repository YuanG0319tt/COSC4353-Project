package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.DTO.UserDTO;
import com.example.volunteerMatching.models.DTO.UserUpdateRequest;
// import com.example.volunteerMatching.models.UserCredentials;
// import com.example.volunteerMatching.models.UserProfile;
import com.example.volunteerMatching.utils.Result;

public interface IUserService {
    Result login(UserDTO userDTO);
    Result register(UserDTO userDTO);
    String test(UserDTO userDTO);

    Result updateByName(UserUpdateRequest request);

    Result getUserList();
}