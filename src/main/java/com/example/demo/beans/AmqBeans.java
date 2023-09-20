package com.example.demo.beans;

//import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import javax.jms.JMSException;
import org.springframework.jms.config.JmsListenerContainerFactory;
import javax.jms.ConnectionFactory;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
@EnableJms
public class AmqBeans {

    @Value("${spring.activemq.broker-url}")
    String BROKER_URL;
    @Value("${spring.activemq.user}")
    String BROKER_USERNAME;
    @Value("${spring.activemq.password}")
    String BROKER_PASSWORD;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() throws JMSException{
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
    public JmsTemplate jmsTemplate() throws JMSException{
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public JmsListenerContainerFactory jmsListenerContainerFactory(@Qualifier("connectionFactory") ConnectionFactory factory) throws JMSException {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setConnectionFactory(factory);
        return containerFactory;
    }
}
