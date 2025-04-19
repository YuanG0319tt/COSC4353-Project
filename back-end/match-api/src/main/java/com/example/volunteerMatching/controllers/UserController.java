package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.DTO.UserDTO;
import com.example.volunteerMatching.models.DTO.UserUpdateRequest;
import com.example.volunteerMatching.models.UserProfile;
import com.example.volunteerMatching.services.IUserService;
import com.example.volunteerMatching.services.UserProfileService;
import com.example.volunteerMatching.utils.HttpMessage;
import com.example.volunteerMatching.utils.HttpStatus;
import com.example.volunteerMatching.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class UserController {
    @Resource
    private IUserService iUserService;
    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        return iUserService.login(userDTO);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        return iUserService.register(userDTO);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UserUpdateRequest request){
        return iUserService.updateByName(request);
    }

    @GetMapping("/profile/{uid}")
    public Result profile(@PathVariable Integer uid){
        UserProfile profile = userProfileService.getById(uid);
        return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,profile);
    }

    @PostMapping("/profile")
    public Result profile(@RequestBody UserProfile request){
        UserProfile profile = userProfileService.getById(request.getUid());
        if (profile == null){
            if (userProfileService.save(request)) {
                return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,request);
            }
        }
        if (userProfileService.updateById(request)) {
            return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,request);
        }else {
            return Result.response(HttpStatus.FAILED, HttpMessage.UPDATE_FAILED,null);
        }

    }

    @GetMapping("/list")
    public Result list(){
        return iUserService.getUserList();
    }

    @PostMapping("/test")
    public String test(@RequestBody UserDTO userDTO){
        return iUserService.test(userDTO);
    }
}
