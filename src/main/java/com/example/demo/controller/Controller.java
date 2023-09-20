package com.example.demo.controller;

import com.example.demo.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class Controller {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${spring.activemq.queue}")
    String queue;
    private List<Person> persons = new ArrayList<>();

    @GetMapping(value = "/get-message", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getMessage(){
        return persons;
    }

    @PostMapping(value = "/post-message", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person postMessage(@RequestBody Person person){
        log.info("Input message {}", person);
        jmsTemplate.convertAndSend(queue, person);
        return person;
    }

    //JMS Listener with container factory
    @JmsListener(destination = "springQueue", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(Person person){
        log.info("Received Message: {}", person);
        persons.add(person);
    }
}
