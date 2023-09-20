package com.example.demo.beans;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Configuration
@EnableJms
public class WmqBeans {

    @Value("${spring.wmq.broker-url}")
    String BROKER_URL;
    @Value("${spring.wmq.user}")
    String BROKER_USERNAME;
    @Value("${spring.wmq.password}")
    String BROKER_PASSWORD;

    @Bean
    public ActiveMQConnectionFactory wmQconnectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new  ActiveMQConnectionFactory();
        //connectionFactory.setTrustAllPackages(true);
        //connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setBrokerURL(BROKER_URL);
        //connectionFactory.setUserName(BROKER_USERNAME);
        connectionFactory.setUser(BROKER_USERNAME);
        connectionFactory.setPassword(BROKER_PASSWORD);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate wMqJmsTemplate() throws Exception{
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(wmQconnectionFactory());
        return template;
    }
}
