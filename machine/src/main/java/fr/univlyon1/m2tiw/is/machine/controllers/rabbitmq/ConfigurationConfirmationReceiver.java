package fr.univlyon1.m2tiw.is.machine.controllers.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.machine.services.VoitureService;
import fr.univlyon1.m2tiw.is.machine.services.dtos.VoitureDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
// Rmq: injection du nom de la queue à partir de la configuration définie dans application.properties
@RabbitListener(queues = "${tiw.is.machine.queue}")
@Component
public class ConfigurationConfirmationReceiver {

    @Autowired
    private VoitureService voitureService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitHandler
    public void receive(String message) throws JsonMappingException, JsonProcessingException {
        VoitureDTO payload = objectMapper.readValue(message, VoitureDTO.class);
        voitureService.reconfigure(payload);
        log.info("Received message <" + message + ">");
    }

    @RabbitHandler
    public void receive(byte[] message) throws JsonMappingException, JsonProcessingException{
        log.info("Received byte <" + message + ">");
        receive(new String(message, StandardCharsets.UTF_8));
    }
}
