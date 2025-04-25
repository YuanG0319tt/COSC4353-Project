package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Notice;
import com.example.demo.mapper.NoticeMapper;
import com.example.demo.service.NoticetService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticetService {
    @Override
    public boolean removeById(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}
