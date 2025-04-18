package com.example.volunteerMatching.services;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.volunteerMatching.mappers.UserProfileMapper;
import com.example.volunteerMatching.models.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements UserProfileService {
    // Add custom service logic here if needed
}