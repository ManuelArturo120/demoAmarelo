package com.demoAmarelo.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;




@Configuration
public class RabbitConfig {
    
    public static final String NOMBRE_COLA = "cola.Productos";

    @Bean
    public Queue colaMensaje() {
        return new Queue(NOMBRE_COLA, true);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(new DefaultJackson2JavaTypeMapper() {{
            setTrustedPackages("com.demoAmarelo.model");
        }});
        return converter;
    }
}
