package com.dyw.rocketmq.controller;

import com.dyw.rocketmq.Service.MailService;
import com.dyw.rocketmq.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;
import rocketmq.result.Result;
import rocketmq.vo.MailParam;


/**
 * @author Devil
 * @create 2022-02-26 14:37
 */
@RestController
@RequestMapping("mail")
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @PostMapping
    public Result sendMail(@RequestBody MailParam mailParam) throws MessagingException {
        return mailService.sendMail(mailParam);
    }

    @PostMapping("all")
    public Result sendMailToAll(@RequestParam("email")String email,@RequestParam("subject")String subject) throws MessagingException {
        return mailService.sendMailToAll(email,subject);
    }

    @PostMapping("register")
    public Result userRegister(@RequestParam("name")String name, @RequestParam("email")String email){
        return userService.userRegister(name,email);
    }

}
