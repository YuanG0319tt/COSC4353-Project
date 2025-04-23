package com.example.demo.controller;

import com.example.demo.entity.DTO.UserDTO;
import com.example.demo.entity.DTO.UserUpdateRequest;
import com.example.demo.entity.Event;
import com.example.demo.entity.UserProfile;
import com.example.demo.service.EventService;
import com.example.demo.service.IUserService;

import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.example.demo.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/events")
@CrossOrigin
@Slf4j
public class EventController {


    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public Result list(){
        return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,eventService.list());
    }


    @PostMapping("/add")
    public Result add(@RequestBody Event events){
        if (eventService.save(events)) {
            return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS,null);
        }else {
            return Result.response(HttpStatus.FAILED, HttpMessage.INSERT_FAILED,null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        boolean ok = eventService.removeById(id);
        if (ok) {
            log.info("Deleted event id={}", id);
            return Result.response(HttpStatus.SUCCESS, HttpMessage.SUCCESS, null);
        } else {
            log.warn("Failed to delete event id={}", id);
            return Result.response(HttpStatus.FAILED, "Delete failed", null);
        }
    }


}
