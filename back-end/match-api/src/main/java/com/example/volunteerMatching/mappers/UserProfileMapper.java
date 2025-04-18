package com.example.volunteerMatching.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.volunteerMatching.models.UserProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfile> {
    // Add custom queries here if needed
}
