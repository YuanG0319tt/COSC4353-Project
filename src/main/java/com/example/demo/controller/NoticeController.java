package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.service.NoticetService;
import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.example.demo.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
@Slf4j
public class NoticeController {

    @Autowired
    private NoticetService noticeService;

    @GetMapping("/list")
    public Result list(){
        return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,noticeService.list());
    }


    @PostMapping("/add")
    public Result add(@RequestBody Notice Notices){
        if (noticeService.save(Notices)) {
            return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,null);
        }else {
            return Result.response(HttpStatus.FAILED, HttpMessage.INSERT_FAILED,null);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean deleted = noticeService.removeById(id);
        if (deleted) {
            return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS, null);
        } else {
            return Result.response(HttpStatus.FAILED, HttpMessage.DELETE_FAILED, null);
        }
    }
}
