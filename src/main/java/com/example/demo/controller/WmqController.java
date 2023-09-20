package com.example.demo.controller;

import com.example.demo.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WmqController {
    @Autowired
    @Qualifier("wMqJmsTemplate")
    JmsTemplate wMqJmsTemplate;

    @Value("${spring.activemq.queue}")
    String queue;

    @PostMapping(value = "/post-wmq-message", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person postMessage(@RequestBody Person person){
        log.info("Input message {}", person);
        wMqJmsTemplate.convertAndSend(queue, person);
        return person;
    }

}
