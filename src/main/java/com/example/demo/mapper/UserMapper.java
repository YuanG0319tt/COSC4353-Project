package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.PO.UserPO;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
    String selectPasswordByUsername(String email);

    User selectUser(String email, String password);

    User selectByUsername(String email);

    Boolean updateByName(UserPO userPO);

    List<User> getUserList();
}
