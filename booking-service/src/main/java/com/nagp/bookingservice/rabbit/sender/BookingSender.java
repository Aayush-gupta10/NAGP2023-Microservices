package com.nagp.bookingservice.rabbit.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BookingSender {

    @Autowired
    private RabbitMessagingTemplate template;

    @Bean
    Queue queue() {
        return new Queue("SearchQ", false);
    }

    @Bean
    Queue queue1() {
        return new Queue("CheckINQ", false);
    }

    public void send(Object message) {
        template.convertAndSend("Master-data-queue", message);
    }
}