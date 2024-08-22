package com.demoAmarelo.service;

import com.demoAmarelo.config.RabbitConfig;
import com.demoAmarelo.model.Mensaje;
import com.demoAmarelo.model.Producto;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendQueve(Producto mensaje) {
        rabbitTemplate.convertAndSend(RabbitConfig.NOMBRE_COLA, mensaje);
    }
}
