package com.laoxu.rabbittest.task;


import com.laoxu.rabbittest.dto.RabbitSendMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: HLW-RYH-1370
 * Date: 2019/5/8
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
@Component
@EnableRabbit
@Configuration
@Slf4j
public class RabbitQueueReceiver {



    @Autowired
    private RabbitQueueSender taskQueueSender;


    @RabbitListener(queues = "${queue.immediate_queue}")
    @RabbitHandler
    public void get(RabbitSendMsgDto msg) {
        log.info("immediate_queue：" + msg.getContent());
    }

    @RabbitListener(queues = "${queue.video_queue}")
    @RabbitHandler
    public void getVideoConsult(RabbitSendMsgDto msg) {
        log.info("video_queue：" + msg.getContent());
    }


    @RabbitListener(queues = "${queue.tw_queue}")
    @RabbitHandler
    public void getTwConsult(RabbitSendMsgDto msg) {
        log.info("tw_queue：" + msg.getContent());
    }

    @RabbitListener(queues = "${queue.ds_queue:ds_consult_apply}")
    @RabbitHandler
    public void getPharmacyConsult(RabbitSendMsgDto msg) {
        log.info("ds_queue:ds_consult_apply：" + msg.getContent());
    }

}
