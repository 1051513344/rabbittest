package com.laoxu.rabbittest.task;


import com.laoxu.rabbittest.config.RabbitConfig;
import com.laoxu.rabbittest.dto.RabbitSendMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: HLW-RYH-1370
 * Date: 2019/5/8
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */
@Component
@Slf4j
public class RabbitQueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(RabbitSendMsgDto msg) {
        msg.setSendTime(new Date());
        log.info("发送定时消息："+msg);
        this.rabbitTemplate.convertAndSend(RabbitConfig.DEAD_LETTER_EXCHANGE, RabbitConfig.DELAY_ROUTING_KEY, msg);
    }

    public void sendRemind(RabbitSendMsgDto msg) {
        msg.setSendTime(new Date());
        log.info("发送定时消息："+msg);
        this.rabbitTemplate.convertAndSend(RabbitConfig.DEAD_LETTER_EXCHANGE, RabbitConfig.DELAY_REMIND_KEY, msg);
    }

    /**
     * 视频业务进队列
     */
    public void  sendVideoConsult(RabbitSendMsgDto msg) {
        rabbitTemplate.convertAndSend( RabbitConfig.VIDEO_CONSULT_APPLY , msg);
    }

    /**
     * 安卓门诊药店发送消息
     */
    public void sendToAndroid(RabbitSendMsgDto msg){
        rabbitTemplate.convertAndSend("que_android_mzyd_" + "consultId", JSON.toJSONString(msg));
    }

    public void sendTwConsult(RabbitSendMsgDto msg) {
        rabbitTemplate.convertAndSend( RabbitConfig.TW_CONSULT_APPLY , msg);
    }

    public void  sendPharmacyConsult(RabbitSendMsgDto msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.DS_CONSULT_APPLY, msg);
    }
}
