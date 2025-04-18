package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.DTO.UserDTO;
import com.example.volunteerMatching.models.DTO.UserUpdateRequest;
import com.example.volunteerMatching.utils.Result;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Override
    public Result login(UserDTO userDTO) {
        // Placeholder logic
        return Result.success("Logged in");
    }

    @Override
    public Result register(UserDTO userDTO) {
        return Result.success("Registered");
    }

    @Override
    public String test(UserDTO userDTO) {
        return "Test passed!";
    }

    @Override
    public Result updateByName(UserUpdateRequest request) {
        return Result.success("Updated");
    }

    @Override
    public Result getUserList() {
        return Result.success("User list");
    }
}