package com.example.demo.controller;

import com.example.demo.entity.DTO.UserDTO;
import com.example.demo.entity.DTO.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.service.IUserService;
import com.example.demo.service.UserProFileService;
import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.example.demo.utils.Result;
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
    private UserProFileService userProFileService;

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
        UserProfile profile = userProFileService.getById(uid);
        return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,profile);
    }

    @PostMapping("/profile")
    public Result profile(@RequestBody UserProfile request){
        UserProfile profile = userProFileService.getById(request.getUid());
        if (profile == null){
            if (userProFileService.save(request)) {
                return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,request);
            }
        }
        if (userProFileService.updateById(request)) {
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
