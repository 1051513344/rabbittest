package com.laoxu.rabbittest.controller;

import com.laoxu.rabbittest.dto.RabbitSendMsgDto;
import com.laoxu.rabbittest.task.RabbitQueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class rabbitController {

    @Autowired
    private RabbitQueueSender taskQueueSender;

    @Bean
    private RabbitSendMsgDto msgDto(){
        RabbitSendMsgDto msgDto =new RabbitSendMsgDto();
        msgDto.setType("1");
        msgDto.setContent("支付超时消息");
        msgDto.setConsultId("consult1");
        return msgDto;
    }


    @GetMapping("/1")
    public String send1(){
        taskQueueSender.send(msgDto());
        return "success";
    }

    @GetMapping("/2")
    public String send2(){
        taskQueueSender.sendRemind(msgDto());
        return "success";
    }

    @GetMapping("/3")
    public String send3(){
        taskQueueSender.sendVideoConsult(msgDto());
        return "success";
    }

    @GetMapping("/4")
    public String send4(){
        taskQueueSender.sendToAndroid(msgDto());
        return "success";
    }

    @GetMapping("/5")
    public String send5(){
        taskQueueSender.sendTwConsult(msgDto());
        return "success";
    }

    @GetMapping("/6")
    public String send6(){
        taskQueueSender.sendPharmacyConsult(msgDto());
        return "success";
    }



}
