package com.musabeli.ruta_producer.services;

import com.musabeli.ruta_producer.dto.RutaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RutaProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public void sendRuta(RutaDTO ruta){
        log.info("Enviando ruta a RabbitMQ - Patente: {}, Ruta Inicio: {}, Ruta Fin: {}, Fecha: {}",
                ruta.getPatente(),
                ruta.getRutaInicio(),
                ruta.getRutaFin(),
                ruta.getFecha());

        rabbitTemplate.convertAndSend(exchangeName, routingKey, ruta);

        log.info("Ruta enviada exitosamente a RabbitMQ");
    }
}