package com.sf.dbhandler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.dbhandler.bean.BuyRecord;
import com.sf.dbhandler.config.RabbitMqConfig;
import com.sf.dbhandler.mapper.BuyRecordMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
public class QuickBuyService {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    BuyRecordMapper buyRecordMapper;
    @RabbitHandler
    public void saveBuyRecord(String msg) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BuyRecord record = objectMapper.readValue(msg, BuyRecord.class);
        System.out.println("秒杀成功信息：" + record.getItem() + "  " + record.getPerson());
        buyRecordMapper.insertBuyRecord(record);
    }
}
