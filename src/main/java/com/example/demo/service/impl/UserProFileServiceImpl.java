package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.UserProfile;
import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.service.UserProFileService;
import org.springframework.stereotype.Service;

@Service
public class UserProFileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements UserProFileService {

}
