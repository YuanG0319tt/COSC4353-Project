package com.example.demo.service.impl;

import com.example.demo.entity.DTO.UserDTO;
import com.example.demo.entity.DTO.UserUpdateRequest;
import com.example.demo.entity.PO.UserPO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.entity.VO.UserVO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.example.demo.service.UserProFileService;
import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.example.demo.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Resource;
import java.util.regex.Pattern;

@Service
@Slf4j
public class IUserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserProFileService userProFileService;


    @Override
    public Result login(UserDTO userDTO) {
        if (!StringUtils.hasLength(userDTO.getEmail())) {
            return Result.response(HttpStatus.ERROR, HttpMessage.NEED_USER_NAME,null) ;
        }
        if (!StringUtils.hasLength(userDTO.getPassword())) {
            return Result.response(HttpStatus.ERROR, HttpMessage.NEED_PASSWORD,null) ;
        }
        User user = userMapper.selectUser(userDTO.getEmail(), userDTO.getPassword());
        if (user == null){
            return Result.response(HttpStatus.FAILED,HttpMessage.WRONG_PASSWORD,null);
        }else {
            UserProfile profile = userProFileService.getById(user.getId());
//
            UserVO userVO=new UserVO();
            if (profile != null){
                userVO.setIsFirstLogin("false");
            }else {
                userVO.setIsFirstLogin("true");
            }
            userVO.setUsername(user.getEmail());
            userVO.setUid(user.getId());
            userVO.setRole(user.getRole());
//            userVO.setPassword(encrypt(user.getPassword()));
            return Result.response(HttpStatus.SUCCESS,HttpMessage.SUCCESS,userVO);
        }

    }

    @Override
    public Result register(UserDTO userDTO) {
        if (!StringUtils.hasLength(userDTO.getEmail())) {
            return Result.response(HttpStatus.ERROR, HttpMessage.NEED_USER_NAME,null) ;
        }
        if (!StringUtils.hasLength(userDTO.getPassword())) {
            return Result.response(HttpStatus.ERROR, HttpMessage.NEED_PASSWORD,null) ;
        }
        if (!StringUtils.hasLength(userDTO.getRole())) {
            return Result.response(HttpStatus.ERROR, "can not be empty",null);
        }
        //
//        if(!isValidEmail(userDTO.getUsername())){
//            return Result.response(HttpStatus.FAILED,HttpMessage.NEED_EMAIL_FORMAT,null);
//        }
        if(userDTO.getPassword().length()<6){
            return Result.response(HttpStatus.FAILED,HttpMessage.PASSWORD_TOO_SHORT,null);
        }
        //
        User user=userMapper.selectByUsername(userDTO.getEmail());
        if (user != null) {
            return Result.response(HttpStatus.FAILED,HttpMessage.NAME_EXISTED,null);
        }
        //
        UserPO addUser=new UserPO();
        addUser.setEmail(userDTO.getEmail());
        addUser.setPassword(userDTO.getPassword());
        addUser.setRole(userDTO.getRole());
        if(userMapper.insert(addUser)==1){
            return Result.response(HttpStatus.SUCCESS,HttpMessage.SUCCESS,null);
        }else {
            return Result.response(HttpStatus.ERROR,HttpMessage.SYSTEM_ERROR,null);
        }
    }

    @Override
    public String test(UserDTO userDTO) {
        String username=userDTO.getEmail();
        return userMapper.selectPasswordByUsername(username);
    }

    @Override
    public Result updateByName(UserUpdateRequest request) {
        User user=userMapper.selectByUsername(request.getEmail());
        if (!(user.getPassword().equals(request.getOldPassword()))) {
            return Result.response(HttpStatus.ERROR, "original password wrong",null);
        }
        UserPO userPO=new UserPO();
        userPO.setEmail(request.getEmail());
        userPO.setPassword(request.getConfirmPassword());
        userMapper.updateByName(userPO);
        return Result.response(HttpStatus.SUCCESS,HttpMessage.SUCCESS,user);
    }

    @Override
    public Result getUserList() {

        return Result.response(HttpStatus.SUCCESS,HttpMessage.SUCCESS,userMapper.getUserList());
    }



//    public static String encrypt(String input) {
//        try {
//
//            MessageDigest md = MessageDigest.getInstance("MD5");
//
//
//            byte[] hashBytes = md.digest(input.getBytes());
//
//
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashBytes) {
//
//                hexString.append(String.format("%02x", b));
//            }
//
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


//    public static boolean isValidEmail(String email) {
//
//        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
//        if (email == null || email.isEmpty()) {
//            return false;
//        }
//
//        return Pattern.matches(EMAIL_REGEX, email);
//    }
}
