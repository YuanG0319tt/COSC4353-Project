package com.example.demo.service;

import com.example.demo.entity.DTO.UserDTO;
import com.example.demo.entity.DTO.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.utils.Result;

public interface IUserService {
    Result login(UserDTO userDTO);
    Result register(UserDTO userDTO);
    String test(UserDTO userDTO);

    Result updateByName(UserUpdateRequest request);

    Result getUserList();
}
