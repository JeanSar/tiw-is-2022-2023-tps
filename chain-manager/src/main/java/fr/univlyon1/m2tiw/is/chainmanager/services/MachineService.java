package fr.univlyon1.m2tiw.is.chainmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Service
public class MachineService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    final String ROOT_URI = "http://localhost:8080/machine";

    public Collection<MachineDTO> getMachines() {
        try {
            log.info(String.format("GET %s", ROOT_URI));
            ResponseEntity<MachineDTO[]> response = restTemplate.getForEntity(ROOT_URI, MachineDTO[].class);
            MachineDTO[] machines = response.getBody();
            if(machines == null) {
                return null;
            } else {
                return Arrays.asList(machines);
            }
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void envoieOptionsVoiture(String queueName, Voiture voiture) {
        Collection<String> options = voiture.getOptions();
        log.info("Envoi des {} options '{}' pour la voiture {} sur la queue '{}'",
                options.size(), options, voiture.getId(), queueName);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String message =  mapper.writeValueAsString(voiture);
            this.rabbitTemplate.convertAndSend(queueName, message);
        } catch (JsonProcessingException | AmqpException e) {
            log.error(e.getMessage());
        }
    }
}
