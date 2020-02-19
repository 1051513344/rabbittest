package com.laoxu.rabbittest.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HLW-RYH-1370
 * Date: 2019/5/8
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@Component
public class RabbitConfig {

    /**
     * ****************************
     * 第一部分说明：
     * 从配置文件中取出对应的队列配置
     * ****************************
     */

    private static final Integer DEFAULT_CONCURRENT = 5;

    public static  String IMMEDIATE_QUEUE;

    @Value("${queue.immediate_queue}")
    public void setImmediateQueue(String immediateQueue) {
        IMMEDIATE_QUEUE = immediateQueue;
    }

    public static String IMMEDIATE_EXCHANGE;

    @Value("${queue.immediate_exchange}")
    public void setImmediateExchange(String immediateExchange) {
        IMMEDIATE_EXCHANGE = immediateExchange;
    }

    public static  String IMMEDIATE_ROUTING_KEY;

    @Value("${queue.immediate_routing_key}")
    public void setImmediateRoutingKey(String immediateRoutingKey) {
        IMMEDIATE_ROUTING_KEY = immediateRoutingKey;
    }

    public static String PAY_TASK_QUEUE;

    @Value("${queue.pay_task_queue}")
    public void setPayTaskQueue(String payTaskQueue) {
        PAY_TASK_QUEUE = payTaskQueue;
    }

    public static String PAY_REMIND_QUEUE ;

    @Value("${queue.pay_remind_queue}")
    public void setPayRemindQueueQueue(String payRemindQueue) {
        PAY_REMIND_QUEUE = payRemindQueue;
    }

    //死信交换机名称
    public static String DEAD_LETTER_EXCHANGE;

    @Value("${queue.dead_letter_exchange}")
    public void setDeadLetterExchange(String deadLetterExchange) {
        DEAD_LETTER_EXCHANGE = deadLetterExchange;
    }

    public static String DELAY_ROUTING_KEY;

    @Value("${queue.delay_routing_key}")
    public void setDelayRoutingKey(String delayRoutingKey) {
        DELAY_ROUTING_KEY = delayRoutingKey;
    }

    public static String DELAY_REMIND_KEY;

    @Value("${queue.delay_remind_key}")
    public void setDelayRemindKeyKey(String delayRemindKey) {
        DELAY_REMIND_KEY = delayRemindKey;
    }


    public static String VIDEO_CONSULT_APPLY ;

    @Value("${queue.video_queue}")
    public void setVideoConsultApply(String videoConsultApply) {
        VIDEO_CONSULT_APPLY = videoConsultApply;
    }


    public static String TW_CONSULT_APPLY;

    @Value("${queue.tw_queue}")
    public void setTwConsultApply(String twConsultApply) {
        TW_CONSULT_APPLY = twConsultApply;
    }

    public static String DS_CONSULT_APPLY;

    @Value("${queue.ds_queue:ds_consult_apply}")
    public void setDsConsultApply(String dsConsultApply) {
        DS_CONSULT_APPLY = dsConsultApply;
    }


    /**
     * ****************************
     * 第二部分说明：
     * 根据第一部分创建对应的队列
     * ****************************
     */

    @Bean
    public Queue videoConsultApply() {
        return new Queue(VIDEO_CONSULT_APPLY, true);
    }

    @Bean
    public Queue twConsultApply() {
        return new Queue(TW_CONSULT_APPLY, true);
    }

    @Bean
    public Queue dsConsultApply(){
        return new Queue(DS_CONSULT_APPLY, true);
    }

    // 创建一个立即消费队列
    @Bean
    public Queue immediateQueue() {
        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
        return new Queue(IMMEDIATE_QUEUE, true);
    }

    // 创建一个延时队列
    @Bean
    public Queue payTaskQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-message-ttl",600000);
        params.put("x-dead-letter-exchange", IMMEDIATE_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", IMMEDIATE_ROUTING_KEY);
        return new Queue(PAY_TASK_QUEUE, false, false, false, params);
    }

    @Bean
    public Queue payRemindQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-message-ttl",300000);
        params.put("x-dead-letter-exchange", IMMEDIATE_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", IMMEDIATE_ROUTING_KEY);
        return new Queue(PAY_REMIND_QUEUE, false, false, false, params);
    }

    /**
     * ****************************
     * 第三部分说明：
     * 创建还未绑定的交换机
     * ****************************
     */

    @Bean
    public DirectExchange immediateExchange() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange(IMMEDIATE_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }



    /**
     * ****************************
     * 第四部分说明：
     * 交换机和对应的队列绑定到一起
     * ****************************
     */

    @Bean
    //把立即消费的队列和立即消费的exchange绑定在一起
    public Binding immediateBinding() {
        return BindingBuilder.bind(immediateQueue()).to(immediateExchange()).with(IMMEDIATE_ROUTING_KEY);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(payTaskQueue()).to(deadLetterExchange()).with(DELAY_ROUTING_KEY);
    }

    @Bean
    public Binding remindBinding() {
        return BindingBuilder.bind(payRemindQueue()).to(deadLetterExchange()).with(DELAY_REMIND_KEY);
    }


    /**
     * ****************************
     * 第五部分说明：
     * 管理监听工厂
     * ****************************
     */
    @Bean("customContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                 ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //并发数
        factory.setConcurrentConsumers(DEFAULT_CONCURRENT);
        //最大并发数
        factory.setMaxConcurrentConsumers(DEFAULT_CONCURRENT);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

}
