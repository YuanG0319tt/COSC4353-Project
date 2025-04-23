package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Event;
import com.example.demo.entity.Notice;
import com.example.demo.mapper.EventMapper;
import com.example.demo.mapper.NoticeMapper;
import com.example.demo.service.EventService;
import com.example.demo.service.NoticetService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticetService {

}
