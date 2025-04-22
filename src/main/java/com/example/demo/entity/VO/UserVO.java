package com.example.demo.entity.VO;

import lombok.Data;

@Data
public class UserVO {
    String username;
    String role;
    String isFirstLogin;
    Integer uid;
}
