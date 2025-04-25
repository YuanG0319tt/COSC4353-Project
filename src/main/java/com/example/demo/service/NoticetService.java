package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Notice;

public interface NoticetService extends IService<Notice> {
    boolean removeById(Long id);
}
