package com.laoxu.rabbittest.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RabbitSendMsgDto implements Serializable {

    private String  consultId;

    // 1支付超时 2图文结束倒计时 3支付提醒
    private String  type;

    private String content;

    private Date sendTime;

}
